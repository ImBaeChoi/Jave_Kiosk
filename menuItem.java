package kiosk;

import java.util.List;


/**
 메뉴 정보 불러오기 용
 menuCode   : 메뉴 고유 코드 (ex. SB001)
 menuName   : 메뉴명 (ex. 카페 라떼)
 category   : 메뉴 카테고리 (ex. 에스프레소)
 sizes      : 사이즈 및 Hot/Iced 정보를 포함하는 리스트
 priceNote  : 가격 관련 추가 설명 리스트
 **/

public class menuItem {

    private String menuCode;
    private String menuName;
    private String category;
    private List<sizeInfo> sizes;
    private List<String> priceNote;

    public menuItem() {
    }

    public menuItem(String menuCode, String menuName, String category, List<sizeInfo> sizes, List<String> priceNote) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.category = category;
        this.sizes = sizes;
        this.priceNote = priceNote;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<sizeInfo> getSizes() {
        return sizes;
    }

    public void setSizes(List<sizeInfo> sizes) {
        this.sizes = sizes;
    }

    public List<String> getPriceNote() {
        return priceNote;
    }

    public void setPriceNote(List<String> priceNote) {
        this.priceNote = priceNote;
    }

    @Override
    public String toString() {
        return "MenuItem{" + "menuCode='" + menuCode + '\'' + ", menuName='" + menuName + '\'' + ", category='" + category + '\'' + ", sizes=" + sizes + ", priceNote=" + priceNote + '}';
    }
}
