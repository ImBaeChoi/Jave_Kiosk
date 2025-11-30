package kiosk;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import kiosk.payment.PaymentResult;
import kiosk.payment.PaymentScreen;

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
                    // 찬희가 넣은 결제 부분
                    // 선택한 항목의 최종 가격으로 결제 진행
                    PaymentScreen payment = new PaymentScreen(sc);
                    PaymentResult payResult = payment.show(selection.getPrice());

                    // 결제 결과 출력 및 분기
                    if (!payResult.success) {
                        System.out.println("[결제 실패] " + payResult.message);
                        // 실패 시 처리 로직이 있으면 여기에 작성
                    } else {
                        System.out.println("[결제 성공] " + payResult.message);
                        System.out.println("지불금액: " + payResult.paid + "원");
                        System.out.println("거스름돈: " + payResult.change + "원");
                        // 성공 시 영수증 출력/주문 확정 로직이 있으면 여기에 작성
                        // 여기까지 찬희가 넣었음
                    }

                } else {
                    System.out.println("선택이 취소되었습니다.\n");
                }
            } else {
                System.out.println("잘못된 선택입니다.\n");
            }
        }
    }
}
