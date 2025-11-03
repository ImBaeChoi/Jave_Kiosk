package Cart;

import java.util.Scanner;

/*커피 키오스크 장바구니 프로그램의 메인 클래스
사용자 인터페이스를 제공하고 전체 프로그램의 흐름을 제어함*/
public class CartMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 가격 계산 객체 생성
        Price_Calculation priceCalc = new Price_Calculation();

        // 수량 계산 객체 생성
        Quantity_Calculation qtyCalc = new Quantity_Calculation();

        // 두 객체가 같은 장바구니 리스트를 공유하도록 설정
        qtyCalc.items = priceCalc.items;

        // 프로그램 메인 루프
        while (true) {
            // 메뉴 출력
            System.out.println("\n=== 커피 키오스크 장바구니 ===");
            System.out.println("1. 메뉴 추가");
            System.out.println("2. 메뉴 수정");
            System.out.println("3. 메뉴 삭제");
            System.out.println("4. 장바구니 출력");
            System.out.println("5. 총 금액 확인");
            System.out.println("6. 총 수량 확인");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = sc.nextInt(); // 사용자 선택 입력받기
            sc.nextLine();

            // 사용자 선택에 따라 기능 실행
            switch (choice) {
                case 1: // 메뉴 추가
                    System.out.print("메뉴 이름: ");
                    String name = sc.nextLine();
                    System.out.print("가격: ");
                    int price = sc.nextInt();
                    System.out.print("수량: ");
                    int qty = sc.nextInt();
                    priceCalc.addItem(name, price, qty); // 장바구니에 추가
                    break;

                case 2: // 메뉴 수정
                    System.out.print("수정할 메뉴 이름: ");
                    String uName = sc.nextLine();
                    System.out.print("새 수량: ");
                    int newQty = sc.nextInt();
                    priceCalc.updateItem(uName, newQty); // 수량 변경
                    break;

                case 3: // 메뉴 삭제
                    System.out.print("삭제할 메뉴 이름: ");
                    String dName = sc.nextLine();
                    priceCalc.removeItem(dName); // 장바구니에서 삭제
                    break;

                case 4: // 장바구니 출력
                    priceCalc.showCart(); // 현재 장바구니 내용 출력
                    break;

                case 5: // 총 금액 확인
                    System.out.println("총 금액: " + priceCalc.getTotalPrice() + "원");
                    break;

                case 6: // 총 수량 확인
                    System.out.println("총 수량: " + qtyCalc.getTotalQuantity() + "개");
                    break;

                case 0: // 프로그램 종료
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();
                    return; // main 메서드 종료 (프로그램 종료)

                default: // 잘못된 입력
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}