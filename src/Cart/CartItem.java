package Cart;

/*장바구니에 담기는 개별 메뉴 아이템의 정보를 담는 클래스
메뉴의 이름, 가격, 수량을 관리하고 총 가격을 계산함*/
public class CartItem {
    private String name;      // 메뉴 이름
    private int price;        // 메뉴 단가
    private int quantity;     // 주문 수량

//    CartItem 생성자
    public CartItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

//    메뉴 이름 반환
    public String getName() {
        return name;
    }

//    메뉴 가격 반환
    public int getPrice() {
        return price;
    }

//    주문 수량 반환
    public int getQuantity() {
        return quantity;
    }

//    주문 수량 변경
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    해당 아이템의 총 가격 계산
    public int getTotalPrice() {
        return price * quantity;
    }
}