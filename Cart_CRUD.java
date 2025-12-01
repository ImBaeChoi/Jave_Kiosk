package kiosk;

import java.util.ArrayList;
import java.util.List;

//장바구니의 기본 기능을 제공하는 클래스
public class Cart_CRUD {
    // 장바구니 아이템들을 저장하는 리스트
    protected List<CartItem> cartItems = new ArrayList<>();

    public void addItem(String name, int price, int quantity, String category) {
        // 기존 장바구니 아이템들을 순회하면서 중복 확인
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            // 같은 이름의 메뉴가 이미 있으면 수량만 증가
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return; // 수량 증가 후 메서드 종료
            }
        }
        // 중복이 없으면 새로운 아이템 추가 (새로운 생성자 사용)
        cartItems.add(new CartItem(name, price, quantity, category));
    }

    // 장바구니의 특정 메뉴 수량 변경
    public void updateItem(String name, int newQuantity) {
        // 장바구니의 모든 아이템을 순회
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            // 해당 이름의 메뉴를 찾으면 수량 변경
            if (item.getName().equals(name)) {
                item.setQuantity(newQuantity);
                System.out.println(name + "의 수량이 " + newQuantity + "개로 변경되었습니다.");
                return; // 수량 변경 후 메서드 종료
            }
        }
        // 해당 메뉴를 찾지 못한 경우
        System.out.println("해당 메뉴가 장바구니에 없습니다.");
    }

    // 장바구니에서 특정 메뉴 삭제
    public void removeItem(String name) {
        boolean removed = cartItems.removeIf(item -> item.getName().equals(name));
        
        if (removed) {
            System.out.println(name + "이(가) 장바구니에서 삭제되었습니다.");
        } else {
            System.out.println("해당 메뉴가 장바구니에 없습니다.");
        }
    }

    // 장바구니의 모든 아이템 출력
    public void showCart() {
        // 장바구니가 비어있는지 확인
        if (cartItems.isEmpty()) {
            System.out.println("\n----------------------------");
            System.out.println("| 장바구니가 비어 있습니다. |");
            System.out.println("----------------------------");
            return;
        }

        System.out.println("\n================ 장바구니 내역 ================");
        int totalQuantity = 0;
        int totalPrice = 0;

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            int itemTotal = item.getTotalPrice();
            System.out.printf("%d. %-30s | %3d 개 | %5d원\n", 
                (i + 1), item.getName(), item.getQuantity(), itemTotal);
            
            totalQuantity += item.getQuantity();
            totalPrice += itemTotal;
        }
        System.out.println("----------------------------------------------");
        System.out.printf("총 합계: %33s | %3d 개 | %5d원\n", " ", totalQuantity, getTotalPriceFromList()); 
        System.out.println("==============================================");
    }
    
    public int getTotalPriceFromList() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }
    
    public List<CartItem> getCartItems() {
        return cartItems;
    }
}