package Cart;

/*Cart_CRUD를 상속받아 가격 계산 기능을 추가한 클래스
장바구니에 담긴 모든 아이템의 총 금액을 계산함*/
public class Price_Calculation extends Cart_CRUD {
    /*장바구니의 총 금액 계산
    모든 아이템의 (가격 × 수량)을 합산하여 반환*/
    public int getTotalPrice() {
        int total = 0; // 총 금액을 저장할 변수 초기화

        // 장바구니의 모든 아이템을 순회
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            // 각 아이템의 총 가격(단가 × 수량)을 누적
            total += item.getTotalPrice();
        }

        return total; // 최종 합계 금액 반환
    }
}