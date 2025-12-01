package kiosk;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class kiosk_project {

    private static final String MENU_JSON_PATH = "src/kiosk/menuData.json";
    
    // 장바구니 및 가격 계산 객체 (Price_Calculation 사용)
    // Price_Calculation이 Cart_CRUD를 상속받으므로 모든 장바구니 기능을 포함
    private static final Price_Calculation cartManager = new Price_Calculation();
    
    // 결제 처리 객체 (새로 만든 PaymentProcessor 사용)
    private static final PaymentProcessor paymentProcessor = new PaymentProcessor();

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
            System.out.println("\n==== 🛒 커피 키오스크 메인 ====");
            System.out.println("1. ☕ 주문하기");
            System.out.println("2. 🛒 장바구니 확인 및 수정");
            System.out.println("3. 💳 결제하기 (총 금액: " + cartManager.getTotalPrice() + "원)"); 
            System.out.println("0. 🚪 종료");
            System.out.print("메뉴를 선택하세요: ");

            int select;
            try {
                select = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.\n");
                continue;
            }

            switch (select) {
                case 1:
                    // 1) 메뉴 선택 플로우 실행 (기존 selectMenu 사용)
                    orderSelection selection = selector.select(sc);

                    if (selection != null) {
                        // 2) orderSelection 객체를 장바구니에 추가
                        String category = getCategoryFromMenuCode(menuList, selection.getMenuCode());
                        
                        // ★★★ Cart_CRUD.addItem()의 변경된 시그니처에 맞춰 category 전달 ★★★
                        cartManager.addItem(
                            selection.getMenuName() + " (" + selection.getSize() + "/" + selection.getHotIced() + ")",
                            selection.getPrice(),
                            1, // 기본 수량 1개
                            category // ★ admin.orderBell()을 위해 category 전달
                        );
                        
                        System.out.println("\n✅ **" + selection.getMenuName() + "**가 장바구니에 추가되었습니다.");
                    } else {
                        System.out.println("선택이 취소되었습니다.\n");
                    }
                    break;
                
                case 2:
                    // 장바구니 확인/수정 로직
                    handleCartActions(sc);
                    break;
                    
                case 3:
                    // 결제 로직
                    if (cartManager.getTotalPrice() > 0) {
                        paymentProcessor.processPayment(sc, cartManager);
                    } else {
                        System.out.println("⚠️ 장바구니가 비어 있습니다. 주문을 먼저 해주세요.");
                    }
                    break;
                    
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                    
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 장바구니 수정/삭제 사용자 인터페이스 (Cart_CRUD의 메서드 사용)
    private static void handleCartActions(Scanner sc) {
        while (true) {
            cartManager.showCart(); // 현재 장바구니 상태 출력
            if (cartManager.getCartItems().isEmpty()) break; // 장바구니가 비면 메인으로 복귀
            
            System.out.println("\n=== 장바구니 관리 ===");
            System.out.println("1. ➕ 메뉴 수량 변경");
            System.out.println("2. ❌ 메뉴 삭제");
            System.out.println("0. ⬅️ 메인 메뉴로 돌아가기");
            System.out.print("선택: ");
            
            String choiceLine = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
                continue;
            }

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                System.out.print("수량 변경할 메뉴 이름 (정확히 입력): ");
                String name = sc.nextLine();
                System.out.print("새 수량: ");
                int newQty;
                try {
                    newQty = Integer.parseInt(sc.nextLine());
                    if (newQty < 1) {
                        System.out.println("수량은 1 이상이어야 합니다. 삭제하려면 2번 메뉴를 이용하세요.");
                    } else {
                        cartManager.updateItem(name, newQty);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("유효한 숫자를 입력하세요.");
                }
            } else if (choice == 2) {
                System.out.print("삭제할 메뉴 이름 (정확히 입력): ");
                String name = sc.nextLine();
                cartManager.removeItem(name);
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
    
    // 메뉴 코드로부터 카테고리 정보 가져오기 (admin.orderBell() 사용을 위해 필요)
    // 기존 menuItem.java 클래스를 활용합니다.
    private static String getCategoryFromMenuCode(List<menuItem> menuList, String menuCode) {
        for (menuItem item : menuList) {
            if (item.getMenuCode().equalsIgnoreCase(menuCode)) {
                return item.getCategory();
            }
        }
        return "기타"; // 찾지 못할 경우 기본값
    }
}