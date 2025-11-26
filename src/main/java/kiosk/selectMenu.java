package kiosk;

import java.util.List;
import java.util.Scanner;

public class selectMenu {

    private final List<menuItem> menuList;
    private final showMenu showMenu;

    public selectMenu(List<menuItem> menuList, showMenu showMenu) {
        this.menuList = menuList;
        this.showMenu = showMenu;
    }

    // 전체 선택 플로우:
    // 1) 카테고리 선택 → 2) 메뉴 선택 → 3) 사이즈/Hot-Iced 선택 → OrderSelection 반환
    public orderSelection select(Scanner sc) {

        // 1. 카테고리 목록
        List<String> categories = showMenu.getCategories();

        if (categories.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.\n");
            return null;
        }

        // 카테고리 출력 + 입력
        showMenu.printCategories(categories);

        int categoryIndex;
        try {
            categoryIndex = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.\n");
            return null;
        }

        if (categoryIndex == 0) {
            System.out.println("이전 메뉴로 돌아갑니다.\n");
            return null;
        }

        if (categoryIndex < 1 || categoryIndex > categories.size()) {
            System.out.println("존재하지 않는 번호입니다.\n");
            return null;
        }

        String selectedCategory = categories.get(categoryIndex - 1);
        System.out.println("\n선택한 카테고리: " + selectedCategory);

        // 2. 선택된 카테고리의 메뉴 목록
        List<menuItem> filteredMenus = showMenu.getMenusByCategory(selectedCategory);

        if (filteredMenus.isEmpty()) {
            System.out.println("해당 카테고리에 메뉴가 없습니다.\n");
            return null;
        }

        // 메뉴 출력 + 입력
        showMenu.printMenus(selectedCategory, filteredMenus);

        int menuIndex;
        try {
            menuIndex = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.\n");
            return null;
        }

        if (menuIndex == 0) {
            System.out.println("이전 메뉴로 돌아갑니다.\n");
            return null;
        }

        if (menuIndex < 1 || menuIndex > filteredMenus.size()) {
            System.out.println("존재하지 않는 번호입니다.\n");
            return null;
        }

        menuItem selectedMenu = filteredMenus.get(menuIndex - 1);
        System.out.println("\n선택한 메뉴: " + selectedMenu.getMenuName());

        // 3. 사이즈 & Hot/Iced 선택
        return selectSizeAndHotIced(sc, selectedMenu);
    }

    // 사이즈, Hot/Iced 선택 → 가격 계산 → OrderSelection 생성
    // 사이즈/Hot·Iced 선택에서 0을 누르면 null 반환(취소/뒤로가기)
    private orderSelection selectSizeAndHotIced(Scanner sc, menuItem menu) {

        List<sizeInfo> sizeInfos = menu.getSizes();

        if (sizeInfos == null || sizeInfos.isEmpty()) {
            System.out.println("해당 메뉴에는 사이즈/온도 정보가 없습니다.\n");
            return null;
        }

        sizeInfo sizeInfo = sizeInfos.get(0);
        List<String> sizeOptions = sizeInfo.getSize();
        List<String> hotIcedOptions = sizeInfo.getHot_iced();

        if (sizeOptions == null || sizeOptions.isEmpty()
                || hotIcedOptions == null || hotIcedOptions.isEmpty()) {
            System.out.println("사이즈 또는 Hot/Iced 정보가 없습니다.\n");
            return null;
        }

        orderSelection selection = new orderSelection();
        selection.setMenuCode(menu.getMenuCode());
        selection.setMenuName(menu.getMenuName());

        // === 사이즈 선택 ===
        System.out.println("\n=== 사이즈 선택 ===");
        for (int i = 0; i < sizeOptions.size(); i++) {
            System.out.println((i + 1) + ". " + sizeOptions.get(i));
        }
        System.out.print("원하는 사이즈 번호를 선택하세요 (0: 이전): ");

        int sizeIndex;
        try {
            sizeIndex = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.\n");
            return null;
        }

        if (sizeIndex == 0) {
            System.out.println("이전 단계로 돌아갑니다.\n");
            return null; // 사이즈 선택 취소 → 전체 선택 취소
        }

        if (sizeIndex < 1 || sizeIndex > sizeOptions.size()) {
            System.out.println("존재하지 않는 번호입니다.\n");
            return null;
        }

        String selectedSize = sizeOptions.get(sizeIndex - 1);
        selection.setSize(selectedSize);

        // === Hot / Iced 선택 ===
        System.out.println("\n=== Hot / Iced 선택 ===");
        for (int i = 0; i < hotIcedOptions.size(); i++) {
            System.out.println((i + 1) + ". " + hotIcedOptions.get(i));
        }
        System.out.print("원하는 번호를 선택하세요 (0: 이전): ");

        int hiIndex;
        try {
            hiIndex = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.\n");
            return null;
        }

        if (hiIndex == 0) {
            System.out.println("이전 단계로 돌아갑니다.\n");
            return null; // Hot/Iced 선택 취소 → 전체 선택 취소
        }

        if (hiIndex < 1 || hiIndex > hotIcedOptions.size()) {
            System.out.println("존재하지 않는 번호입니다.\n");
            return null;
        }

        String selectedHotIced = hotIcedOptions.get(hiIndex - 1);
        selection.setHotIced(selectedHotIced);

        // 사이즈별 가격 계산
        int finalPrice = calculatePriceBySize(sizeInfo, selectedSize);
        selection.setPrice(finalPrice);

        return selection;
    }

    // 사이즈에 따른 가격 계산 로직
    private int calculatePriceBySize(sizeInfo sizeInfo, String selectedSize) {
        int base = sizeInfo.getPrice_krw();

        if (selectedSize == null) return base;

        String s = selectedSize.toLowerCase();
        if (s.contains("tall"))   return base;
        if (s.contains("grande")) return base + 200;
        if (s.contains("venti"))  return base + 400;

        return base;
    }
}
