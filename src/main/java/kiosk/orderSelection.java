package kiosk;

/**
 장바구니에 넘어가 위해 사용자가 최종적으로 선택한 메뉴의 정보 (메뉴 코드, 이름, 사이즈, 핫/아이스)

 menuCode : 메뉴 고유코드
 menuName : 메뉴 이름
 size     : 선택된 사이즈
 hotIced  : Hot 또는 Iced
 price    : 사이즈/옵션이 반영된 최종 결제 금액
 **/

public class orderSelection {

    private String menuCode;
    private String menuName;
    private String size;
    private String hotIced;
    private int price;

    public orderSelection() {
    }

    public orderSelection(String menuCode, String menuName, String size, String hotIced, int price) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.size = size;
        this.hotIced = hotIced;
        this.price = price;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHotIced() {
        return hotIced;
    }

    public void setHotIced(String hotIced) {
        this.hotIced = hotIced;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderSelection{" + "menuCode='" + menuCode + '\'' + ", menuName='" + menuName + '\'' + ", size='" + size + '\'' + ", hotIced='" + hotIced + '\'' + ", price=" + price + '}';
    }
}
