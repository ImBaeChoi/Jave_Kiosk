package Cart;

import java.util.ArrayList;
import java.util.List;

//장바구니의 기본 기능을 제공하는 클래스
public class Cart_CRUD {
    // 장바구니 아이템들을 저장하는 리스트
    protected List<CartItem> cartItems = new ArrayList<>();

    /*장바구니에 아이템 추가
    이미 같은 이름의 메뉴가 있으면 수량만 증가시킴*/
    public void addItem(String name, int price, int quantity) {
        // 기존 장바구니 아이템들을 순회하면서 중복 확인
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            // 같은 이름의 메뉴가 이미 있으면 수량만 증가
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return; // 수량 증가 후 메서드 종료
            }
        }
        // 중복이 없으면 새로운 아이템 추가
        cartItems.add(new CartItem(name, price, quantity));
    }

    // 장바구니의 특정 메뉴 수량 변경
    public void updateItem(String name, int newQuantity) {
        // 장바구니의 모든 아이템을 순회
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            // 해당 이름의 메뉴를 찾으면 수량 변경
            if (item.getName().equals(name)) {
                item.setQuantity(newQuantity);
                return; // 수량 변경 후 메서드 종료
            }
        }
        // 해당 메뉴를 찾지 못한 경우
        System.out.println("해당 메뉴가 장바구니에 없습니다.");
    }

    // 장바구니에서 특정 메뉴 삭제
    public void removeItem(String name) {
        // 장바구니의 모든 아이템을 순회하면서 삭제할 메뉴 찾기
        for (int i = 0; i < cartItems.size(); i++) {
            // 해당 이름의 메뉴를 찾으면 삭제
            if (cartItems.get(i).getName().equals(name)) {
                cartItems.remove(i);
                break; // 삭제 후 반복문 종료
            }
        }
    }

    // 장바구니의 모든 아이템 출력
    public void showCart() {
        // 장바구니가 비어있는지 확인
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

        System.out.println("===== 장바구니 =====");

        // 모든 아이템을 순회하면서 출력
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            // 메뉴명, 수량, 합계 금액 출력
            System.out.printf("%-10s | 수량: %-2d | 합계: %d원%n",
                    item.getName(), item.getQuantity(), item.getTotalPrice());
        }
    }
}