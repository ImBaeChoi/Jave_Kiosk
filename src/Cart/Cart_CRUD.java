package Cart;

import java.util.ArrayList;
import java.util.List;

public class Cart_CRUD {
    protected List<CartItem> items = new ArrayList<>();

    // 중복 시 수량 증가
    public void addItem(String name, int price, int quantity) {
        for (CartItem item : items) {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(name, price, quantity));
    }

    // 수량 변경
    public void updateItem(String name, int newQuantity) {
        for (CartItem item : items) {
            if (item.getName().equals(name)) {
                item.setQuantity(newQuantity);
                return;
            }
        }
        System.out.println("해당 메뉴가 장바구니에 없습니다.");
    }

    // 메뉴 삭제
    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equals(name));
    }

    // 장바구니 출력
    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }
        System.out.println("===== 장바구니 =====");
        for (CartItem item : items) {
            System.out.printf("%-10s | 수량: %-2d | 합계: %d원%n",
                    item.getName(), item.getQuantity(), item.getTotalPrice());
        }
    }
}
