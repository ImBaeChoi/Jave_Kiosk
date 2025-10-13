package Cart;

import java.util.Scanner;

public class CartMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 공통 장바구니 공유
        Price_Calculation priceCalc = new Price_Calculation();
        Quantity_Calculation qtyCalc = new Quantity_Calculation();
        qtyCalc.items = priceCalc.items; // 같은 리스트 공유

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
                case 1:
                    System.out.print("메뉴 이름: ");
                    String name = sc.nextLine();
                    System.out.print("가격: ");
                    int price = sc.nextInt();
                    System.out.print("수량: ");
                    int qty = sc.nextInt();
                    priceCalc.addItem(name, price, qty);
                    break;
                case 2:
                    System.out.print("수정할 메뉴 이름: ");
                    String uName = sc.nextLine();
                    System.out.print("새 수량: ");
                    int newQty = sc.nextInt();
                    priceCalc.updateItem(uName, newQty);
                    break;
                case 3:
                    System.out.print("삭제할 메뉴 이름: ");
                    String dName = sc.nextLine();
                    priceCalc.removeItem(dName);
                    break;
                case 4:
                    priceCalc.showCart();
                    break;
                case 5:
                    System.out.println("총 금액: " + priceCalc.getTotalPrice() + "원");
                    break;
                case 6:
                    System.out.println("총 수량: " + qtyCalc.getTotalQuantity() + "개");
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
