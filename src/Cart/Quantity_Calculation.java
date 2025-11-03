package Cart;

/*Cart_CRUD를 상속받아 수량 계산 기능을 추가한 클래스
 장바구니에 담긴 모든 아이템의 총 수량을 계산함*/
public class Quantity_Calculation extends Cart_CRUD {
    /*장바구니의 총 수량 계산
    모든 아이템의 수량을 합산하여 반환*/
    public int getTotalQuantity() {
        int total = 0; // 총 수량을 저장할 변수 초기화

        // 장바구니의 모든 아이템을 순회
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            // 각 아이템의 수량을 누적
            total += item.getQuantity();
        }

        return total; // 최종 합계 수량 반환
    }
}