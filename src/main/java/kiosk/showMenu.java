package kiosk;

import java.util.List;
import java.util.stream.Collectors;

public class showMenu {

    // 메뉴 리스트 불러오기
    private final List<menuItem> menuList;

    public showMenu(List<menuItem> menuList) {
        this.menuList = menuList;
    }

    // 메뉴 리스트에서 카테고리 목록 반환
    public List<String> getCategories() {
        return menuList.stream()
                .map(menuItem::getCategory)
                .collect(Collectors.toList());
    }

    // 카테고리 목록 출력 함수
    public void printCategories(List<String> categories) {
        System.out.println("\n=== 카테고리 선택 ===");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.print("원하는 카테고리 번호를 선택하세요 (0: 이전): ");
    }

    // 선택된 카테고리의 메뉴 리스트 반환 함수
    public List<menuItem> getMenusByCategory(String category) {
        return menuList.stream()
                .filter(m -> m.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // 특정 카테고리의 메뉴 목록 출력 함수
    public void printMenus(String category, List<menuItem> menus) {
        System.out.println("\n=== " + category + " 메뉴 목록 ===");
        for (int i = 0; i < menus.size(); i++) {
            menuItem item = menus.get(i);

            int price = 0;
            // 메뉴 조회(메뉴 명, 가격)
            if (item.getSizes() != null && !item.getSizes().isEmpty()) {
                price = item.getSizes().get(0).getPrice_krw(); // tall 기준
            }

            System.out.println((i + 1) + ". " + item.getMenuName() + " - " + price + "원");
        }
        System.out.println();
        System.out.print("주문할 메뉴 번호를 선택하세요 (0: 이전): ");
    }

    // 최종 선택 정보 출력
    public void printSelection(orderSelection selection) {
        System.out.println("==== 선택 메뉴 정보 ====");
        System.out.println("메뉴코드 : " + selection.getMenuCode());
        System.out.println("메뉴명   : " + selection.getMenuName());
        System.out.println("사이즈   : " + selection.getSize());
        System.out.println("Hot/Iced: " + selection.getHotIced());
        System.out.println("가격(원) : " + selection.getPrice());
    }
}
