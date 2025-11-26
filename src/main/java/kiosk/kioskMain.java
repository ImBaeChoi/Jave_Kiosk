package kiosk;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class kioskMain {

    private static final String MENU_JSON_PATH = "src/main/resources/menuData.json";

    public static void main(String[] args) {

        // 1. 메뉴 데이터 로딩
        List<menuItem> menuList;
        try {
            readMenu reader = new readMenu(MENU_JSON_PATH);
            menuList = reader.loadMenuData();
        } catch (IOException e) {
            System.out.println("메뉴 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            return;
        }

        // 2. 출력/선택용 객체 선언
        showMenu show = new showMenu(menuList);
        selectMenu selector = new selectMenu(menuList, show);

        Scanner sc = new Scanner(System.in);

        // 키오스크 메인 로직
        while (true) {
            System.out.println("==== 키오스크 ====");
            System.out.println("1. 주문하기");
            System.out.println("0. 종료");
            System.out.print("메뉴를 선택하세요: ");

            int select;
            try {
                select = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.\n");
                continue;
            }

            if (select == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (select == 1) {
                // 메뉴 선택 전체 플로우
                orderSelection selection = selector.select(sc);

                if (selection != null) {
                    show.printSelection(selection);
                } else {
                    System.out.println("선택이 취소되었습니다.\n");
                }
            } else {
                System.out.println("잘못된 선택입니다.\n");
            }
        }
    }
}
