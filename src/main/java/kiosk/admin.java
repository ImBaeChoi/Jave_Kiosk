package kiosk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class admin {

    // kioskMain과 동일한 JSON 경로
    private static final String MENU_JSON_PATH = "src/main/resources/menuData.json";

    // 매출
    private static int sales = 0;

    // 메뉴 목록 (관리자용)
    private static List<menuItem> menuList = new ArrayList<>();

    public static void main(String[] args) {

        // 1) 메뉴 데이터 로딩
        try {
            loadMenu();
        } catch (IOException e) {
            System.out.println("[경고] 메뉴 파일을 읽어오지 못했습니다. (경로 확인 필요)");
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== 관리자 메뉴 =====");
            System.out.println("1. 매출 확인");
            System.out.println("2. 메뉴 조회");
            System.out.println("3. 메뉴 추가 (Create)");
            System.out.println("4. 메뉴 수정 (Update)");
            System.out.println("5. 메뉴 삭제 (Delete)");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            int num = sc.nextInt();

            switch (num) {
                case 1 :
                    viewSales();         // 매출 확인
                    break;
                case 2:
                    printAllMenu();      // 메뉴 조회
                    break;
                case 3:
                    createMenu(sc);      // 메뉴 추가
                    break;
                case 4:
                    updateMenu(sc);      // 메뉴 수정
                    break;
                case 5:
                    deleteMenu(sc);      // 메뉴 삭제
                    break;
                case 0:
                    try {
                        saveMenu();      // 종료 전 메뉴 저장
                    } catch (IOException e) {
                        System.out.println("[에러] 메뉴 저장 중 오류가 발생했습니다: " + e.getMessage());
                    }
                    System.out.println("관리자 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 주문지 출력 메서드, 메서드와 인자값을 호출하면 이를 주문지 형태로 출력합니다.
    public static void printOrderSlip(int orderNum, String menuName, int quantity, int totalPrice) {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        System.out.println("\n===== 주문지 =====");
        System.out.println("주문일시 : " + nowDate + " " + nowTime);
        System.out.println("주문번호 : " + orderNum);
        System.out.println("메뉴     : " + menuName);
        System.out.println("수량     : " + quantity);
        System.out.println("결제금액 : " + totalPrice + "원");
        System.out.println("=================\n");
    }

    // 음료 제조 후 완료시 호출합니다. 결제가 완료된 것에 대해 이 메서드를 호출합니다.
    public static void orderBell(int orderNum, String menuCategory, int totalPrice) {

        int delayMillis;

        if ("Espresso".equalsIgnoreCase(menuCategory)) {
            delayMillis = 5_000;
        } else if ("Cold Brew".equalsIgnoreCase(menuCategory)) {
            delayMillis = 10_000;
        } else if ("Tivana".equalsIgnoreCase(menuCategory)) {
            delayMillis = 15_000;
        } else {
            delayMillis = 20_000;
        }

        // 메인 스레드를 막지 않기 위해 별도 스레드에서 대기
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delayMillis);
                    System.out.println("▶ 주문 번호 " + orderNum + "번 고객님의 음료가 준비되었습니다.");
                    addSales(totalPrice);
                } catch (InterruptedException e) {
                    System.out.println("알림 스레드 오류");
                    Thread.currentThread().interrupt();
                }
            }
        });

        t.start();
    }

    // 매출 확인 메서드. 총 매출을 보여줍니다.
    public static void viewSales() {
        System.out.println("\n===== 매출 현황 =====");
        System.out.println("총 매출 : " + sales + "원");
        System.out.println("====================\n");
    }

    public static void addSales(int totalPrice) {
        sales += totalPrice;
    }

    // 메뉴 JSON 로드
    private static void loadMenu() throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<menuItem>>() {}.getType();

        FileReader reader = null;
        try {
            reader = new FileReader(MENU_JSON_PATH);
            List<menuItem> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                menuList = loaded;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    // 메뉴 JSON 저장
    private static void saveMenu() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = null;
        try {
            writer = new FileWriter(MENU_JSON_PATH);
            gson.toJson(menuList, writer);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    // 메뉴 전체 조회
    private static void printAllMenu() {
        if (menuList == null || menuList.size() == 0) {
            System.out.println("등록된 메뉴가 없습니다.");
            return;
        }

        System.out.println("\n===== 메뉴 목록 =====");
        for (int i = 0; i < menuList.size(); i++) {
            menuItem item = menuList.get(i);
            System.out.println("코드: " + item.getMenuCode()
                    + " / 이름: " + item.getMenuName()
                    + " / 카테고리: " + item.getCategory());
        }
        System.out.println("====================\n");
    }

    // 메뉴 추가 (Create)
    private static void createMenu(Scanner sc) {
        try {
            System.out.print("새 메뉴 코드(SBxxx 등): ");
            String code = sc.next();

            System.out.print("메뉴 이름: ");
            String name = sc.next();

            System.out.print("카테고리(에스프레소, 콜드 브루 등): ");
            String category = sc.next();

            System.out.print("Tall 기준 가격(원): ");
            String priceStr = sc.next();
            int basePrice = Integer.parseInt(priceStr);

            // sizeInfo 기본값 생성
            List<String> sizes = Arrays.asList("Tall", "Grande", "Venti");
            List<String> hotIced = Arrays.asList("Hot", "Iced");
            sizeInfo sizeInfo = new sizeInfo(sizes, hotIced, basePrice);

            List<sizeInfo> sizeInfos = new ArrayList<>();
            sizeInfos.add(sizeInfo);

            List<String> priceNote = buildPriceNote(basePrice);

            menuItem item = new menuItem(code, name, category, sizeInfos, priceNote);
            menuList.add(item);

            saveMenu();

            System.out.println("메뉴가 추가되었습니다.");
        } catch (NumberFormatException e) {
            System.out.println("숫자를 잘못 입력하셨습니다.");
        } catch (IOException e) {
            System.out.println("메뉴 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 메뉴 수정 (Update)
    private static void updateMenu(Scanner sc) {
        System.out.print("수정할 메뉴 코드: ");
        String code = sc.next();

        menuItem target = findMenuByCode(code);

        if (target == null) {
            System.out.println("해당 코드의 메뉴를 찾을 수 없습니다.");
            return;
        }

        System.out.println("현재 메뉴 이름: " + target.getMenuName());
        System.out.print("새 메뉴 이름(변경하지 않으려면 0 입력): ");
        String newName = sc.next();
        if (newName != null && newName.length() > 0 && !newName.equals("0")) {
            target.setMenuName(newName);
        }

        System.out.println("현재 카테고리: " + target.getCategory());
        System.out.print("새 카테고리(변경하지 않으려면 0 입력): ");
        String newCategory = sc.next();
        if (newCategory != null && newCategory.length() > 0 && !newCategory.equals("0")) {
            target.setCategory(newCategory);
        }

        System.out.print("Tall 기준 새 가격(원, 변경하지 않으려면 0 입력): ");
        String newPriceStr = sc.next();
        if (newPriceStr != null && newPriceStr.length() > 0 && !newPriceStr.equals("0")) {
            try {
                int newBasePrice = Integer.parseInt(newPriceStr);

                if (target.getSizes() != null && target.getSizes().size() > 0) {
                    target.getSizes().get(0).setPrice_krw(newBasePrice);
                }

                target.setPriceNote(buildPriceNote(newBasePrice));
            } catch (NumberFormatException e) {
                System.out.println("가격이 숫자가 아니라서 변경하지 않았습니다.");
            }
        }

        try {
            saveMenu();
            System.out.println("메뉴가 수정되었습니다.");
        } catch (IOException e) {
            System.out.println("메뉴 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 메뉴 삭제 (Delete)
    private static void deleteMenu(Scanner sc) {
        System.out.print("삭제할 메뉴 코드: ");
        String code = sc.next();

        menuItem target = findMenuByCode(code);

        if (target == null) {
            System.out.println("해당 코드의 메뉴를 찾을 수 없습니다.");
            return;
        }

        menuList.remove(target);
        try {
            saveMenu();
            System.out.println("메뉴가 삭제되었습니다.");
        } catch (IOException e) {
            System.out.println("메뉴 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 코드로 메뉴 찾기
    public static menuItem findMenuByCode(String code) {
        if (menuList == null) {
            return null;
        }

        for (int i = 0; i < menuList.size(); i++) {
            menuItem item = menuList.get(i);
            if (item.getMenuCode() != null && item.getMenuCode().equalsIgnoreCase(code)) {
                return item;
            }
        }
        return null;
    }

    // Tall/Grande/Venti 가격 설명 문자열 생성
    private static List<String> buildPriceNote(int basePrice) {
        List<String> notes = new ArrayList<>();
        notes.add("Tall price = " + basePrice);
        notes.add("Grande price = " + (basePrice + 200));
        notes.add("Venti price = " + (basePrice + 400));
        return notes;
    }
}
