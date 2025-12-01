package kiosk;

public class Quantity_Calculation extends Cart_CRUD {

    public int getTotalQuantity() {
        int total = 0; // 총 수량을 저장할 변수 초기화

        // 장바구니의 모든 아이템을 순회
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            // 각 아이템의 수량을 누적
            total += item.getQuantity();
        }

        return total; // 최종 합계 수량 반환
    }
}