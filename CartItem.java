package kiosk;


public class CartItem {
    private String name;      // 이름
    private int price;        // 가격
    private int quantity;     // 개수
    private String category; 
    
    public CartItem() {} 
    
    public CartItem(String name, int price, int quantity, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    //메뉴 이름 반환
    public String getName() {
        return name;
    }

    //메뉴 가격 반환
    public int getPrice() {
        return price;
    }

    //주문 수량 반환
    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
    
    //주문 수량 변경
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    //해당 아이템의 총 가격 계산
    public int getTotalPrice() {
        return price * quantity;
    }
}