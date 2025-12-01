package kiosk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class readMenu {

    // 파일 경로 저장용
    private final String menuJsonPath;

    public readMenu(String menuJsonPath) {
        this.menuJsonPath = menuJsonPath;
    }

    // JSON 파일을 읽어서 메뉴 리스트를 반환
    public List<menuItem> loadMenuData() throws IOException {
        Gson gson = new Gson(); // Gson라이브러리를 이용 Json 데이터 활용
        Type listType = new TypeToken<List<menuItem>>() {}.getType(); // 제네틱 타입 선언

        try (FileReader reader = new FileReader(menuJsonPath)) {
            List<menuItem> menuList = gson.fromJson(reader, listType); // Json 데이터를 List 형식으로 변환
            if (menuList == null) {
                menuList = new ArrayList<>(); // null 값은 빈 리스트로 대체
            }
            return menuList;
        }
    }
}
