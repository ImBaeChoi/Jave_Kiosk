package kiosk;

import java.util.List;

/**
 사이즈에 대한 정보

 size      : 사이즈 리스트 (ex. Tall, Grande, Venti)
 hot_iced  : 온도 옵션 리스트 (ex. Hot, Iced)
 price_krw : Tall 기준 기본 가격 (원)

 selectMenu에서 가격 계산 시
 Tall = 기본가, Grande = +200, Venti = +400 방식으로 사용됨.
 **/

public class sizeInfo {

    private List<String> size;        // ["Tall", "Grande", "Venti"]
    private List<String> hot_iced;    // ["Hot", "Iced"]
    private int price_krw;            // 기본 가격 (Tall 기준)

    public sizeInfo() {
    }

    public sizeInfo(List<String> size, List<String> hot_iced, int price_krw) {
        this.size = size;
        this.hot_iced = hot_iced;
        this.price_krw = price_krw;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getHot_iced() {
        return hot_iced;
    }

    public void setHot_iced(List<String> hot_iced) {
        this.hot_iced = hot_iced;
    }

    public int getPrice_krw() {
        return price_krw;
    }

    public void setPrice_krw(int price_krw) {
        this.price_krw = price_krw;
    }

    @Override
    public String toString() {
        return "SizeInfo{" + "size=" + size + ", hot_iced=" + hot_iced + ", price_krw=" + price_krw + '}';
    }
}
