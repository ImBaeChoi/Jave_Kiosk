package kiosk;
 
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class kioskMain {

    private static final String MENU_JSON_PATH = "src/kiosk/menuData.json";
    
    private static final Price_Calculation cartManager = new Price_Calculation();
    private static final CartMain cartmain = new CartMain();
//    private static final PaymentProcessor paymentProcessor = new PaymentProcessor();

    public static void main(String[] args) {
        
        List<menuItem> menuList;
        try {
            readMenu reader = new readMenu(MENU_JSON_PATH);
            menuList = reader.loadMenuData();
        } catch (IOException e) {
            System.out.println("메뉴 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            return;
        }

        showMenu show = new showMenu(menuList);
        selectMenu selector = new selectMenu(menuList, show);
        Scanner sc = new Scanner(System.in);

        // 키오스크 메인 로직
        while (true) {
            System.out.println("\n==== 커피 키오스크 메인 ====");
            System.out.println("1. 주문하기");
            System.out.println("2. 장바구니 확인 및 수정");
            System.out.println("3. 결제하기 (총 금액: " + String.format("%,d", cartManager.getTotalPrice()) + "원)"); // ★ 추가
            System.out.println("4. 관리자 모드"); // ★ 추가
            System.out.println("0. 종료");
            System.out.print("메뉴를 선택하세요: ");

            int select;
            try {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                select = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.\n");
                continue;
            }

            switch (select) {
                case 1:
                    // 1) 메뉴 선택 플로우 실행
                    orderSelection selection = selector.select(sc);

                    if (selection != null) {
                        String category = getCategoryFromMenuCode(menuList, selection.getMenuCode());
                        
                        cartManager.addItem(
                            selection.getMenuName() + " (" + selection.getSize() + "/" + selection.getHotIced() + ")",
                            selection.getPrice(),
                            1, // 기본 수량 1개
                            category
                        );
                        
                        System.out.println(selection.getMenuName() + "가 장바구니에 추가되었습니다.");
                    } else {
                        System.out.println("선택이 취소되었습니다.\n");
                    }
                    break;
                
                case 2:
                    // 장바구니 확인/수정 로직
                    //handleCartActions(sc);
                    cartmain(sc);
                	break;
                    
                case 3: // 결제 로직 호출
                    if (cartManager.getTotalPrice() > 0) {
                       // paymentProcessor.processPayment(sc, cartManager);                    } else {
                        System.out.println("장바구니가 비어 있습니다. 주문을 먼저 해주세요.");
                    }
                    break;
                
                case 4: // 관리자 모드 실행
                    System.out.println("\n관리자 모드를 실행합니다. (종료 시 메인으로 복귀)");
                    admin.main(new String[]{});
                    break;
                    
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                    
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 장바구니 수정/삭제 사용자 인터페이스
//    private static void handleCartActions(Scanner sc) {
//        while (true) {
//            cartManager.showCart(); // 현재 장바구니 상태 출력
//            if (cartManager.getCartItems().isEmpty()) break;
//            
//            System.out.println("\n=== 장바구니 관리 ===");
//            System.out.println("1. 메뉴 수량 변경");
//            System.out.println("2. 메뉴 삭제");
//            System.out.println("0. 종료");
//            System.out.print("선택: ");
//            
//            String choiceLine = sc.nextLine();
//            int choice;
//            try {
//                choice = Integer.parseInt(choiceLine);
//            } catch (NumberFormatException e) {
//                System.out.println("숫자를 입력해주세요.");
//                continue;
//            }
//
//            if (choice == 0) {
//                break;
//            } else if (choice == 1) {
//                System.out.print("수량 변경할 메뉴 이름: ");
//                String name = sc.nextLine();
//                System.out.print("새 수량: ");
//                int newQty;
//                try {
//                    newQty = Integer.parseInt(sc.nextLine());
//                    if (newQty < 1) {
//                        System.out.println("수량은 1 이상이어야 합니다. 삭제하려면 2번 메뉴를 이용하세요.");
//                    } else {
//                        cartManager.updateItem(name, newQty);
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("유효한 숫자를 입력하세요.");
//                }
//            } else if (choice == 2) {
//                System.out.print("삭제할 메뉴 이름: ");
//                String name = sc.nextLine();
//                cartManager.removeItem(name);
//                System.out.println("장바구니에서 삭제되었습니다.");
//            } else {
//                System.out.println("잘못된 입력입니다.");
//            }
//        }
//    }
    
    private static void cartmain(Scanner sc) {
        Price_Calculation pc = new Price_Calculation();

        Quantity_Calculation qc = new Quantity_Calculation();

        // 두 객체가 같은 장바구니 리스트를 공유하도록 설정
        qc.cartItems = pc.cartItems;

        while (true) {
            System.out.println("\n=== 커피 키오스크 장바구니 ===");
            System.out.println("1. 메뉴 추가");
            System.out.println("2. 메뉴 수정");
            System.out.println("3. 메뉴 삭제");
            System.out.println("4. 장바구니 출력");
            System.out.println("5. 총 금액 확인");
            System.out.println("6. 총 수량 확인");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: // 메뉴 추가
                    System.out.print("메뉴 이름: ");
                    String name = sc.nextLine();
                    System.out.print("가격: ");
                    int price = sc.nextInt();
                    System.out.print("수량: ");
                    int qty = sc.nextInt();
                    pc.addItem(name, price, qty); // 장바구니에 추가
                    break;

                case 2: // 메뉴 수정
                    System.out.print("수정할 메뉴 이름: ");
                    String uName = sc.nextLine();
                    System.out.print("새 수량: ");
                    int newQty = sc.nextInt();
                    pc.updateItem(uName, newQty); // 수량 변경
                    break;

                case 3: // 메뉴 삭제
                    System.out.print("삭제할 메뉴 이름: ");
                    String dName = sc.nextLine();
                    pc.removeItem(dName); // 장바구니에서 삭제
                    break;

                case 4: // 장바구니 출력
                    pc.showCart(); // 현재 장바구니 내용 출력
                    break;

                case 5: // 총 금액 확인
                    System.out.println("총 금액: " + pc.getTotalPrice() + "원");
                    break;

                case 6: // 총 수량 확인
                    System.out.println("총 수량: " + qc.getTotalQuantity() + "개");
                    break;

                case 0: // 프로그램 종료
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();
                    return;

                default: // 잘못된 입력
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
    
    private static String getCategoryFromMenuCode(List<menuItem> menuList, String menuCode) {
        for (menuItem item : menuList) {
            if (item.getMenuCode().equalsIgnoreCase(menuCode)) {
                return item.getCategory();
            }
        }
        return "기타"; // 찾지 못할 경우 기본값
    }
}