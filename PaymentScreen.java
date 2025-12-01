package kiosk;

// 터미널 결제 화면
import java.util.Scanner;

public final class PaymentScreen {
    private final Scanner sc;
    public PaymentScreen(Scanner sc) { this.sc = sc; }

    // 합계를 받아 결제 처리
    public PaymentResult show(int amountDue) {
        println("\n=== 결제 화면 ===");
        println("결제 금액: " + money(amountDue));
        println("안내: 결제수단을 선택한 뒤 지시에 따라 형식에 맞게 입력하세요.");

        while (true) {
            println("\n결제수단: 1)현금  2)카드  3)쿠폰  0)취소");
            println("형식 안내: 현금=정수원화 예) 7000 / 카드=번호 16자리, 비밀번호 4자리 / 쿠폰=WELCOME10, C500, FREE100");
            int sel = readInt("선택: ");

            PaymentResult r = null;

            switch (sel) {
                case 1:
                    r = payCash(amountDue);
                    break;
                case 2:
                    r = payCard(amountDue);
                    break;
                case 3:
                    r = payCoupon(amountDue);
                    break;
                case 0:
                    return PaymentResult.fail("사용자 취소");
                default:
                    println("잘못된 선택입니다.");
                    continue;
            }

            if (r == null) continue;
            if (!r.success) {
                println("[결제 실패] " + r.message);
                println("안내: 결제수단을 다시 선택해 재결제하세요.");
                continue;
            }
            return r;
        }
    }

    // 현금 결제
    private PaymentResult payCash(int amountDue) {
        println("\n안내: 지불 금액을 원 단위 정수로 입력하세요. 예) 7000");
        int input = readInt("지불 금액(원): ");
        if (input < amountDue) return PaymentResult.fail("금액이 부족합니다.");
        int change = input - amountDue;
        return PaymentResult.ok(amountDue, change, "현금 결제 성공");
    }

    // 카드 결제(번호 16자리 + 비밀번호 4자리)
    private PaymentResult payCard(int amountDue) {
        println("\n안내: 카드 정보를 형식에 맞게 입력하세요.");
        println("형식 예시: 번호=1234123412341234  비밀번호=1234");
        String number = readLine("카드번호(16자리, 숫자만): ").trim();
        String pin = readLine("비밀번호(4자리): ").trim();

        if (!is16Digits(number)) return PaymentResult.fail("카드번호 형식 오류(16자리 숫자 필요)");
        if (!is4Digits(pin)) return PaymentResult.fail("비밀번호 형식 오류(4자리 숫자 필요)");

        return PaymentResult.ok(amountDue, 0, "카드 승인 성공");
    }

    // 쿠폰 적용 후 잔액 결제
    private PaymentResult payCoupon(int amountDue) {
        println("\n안내: 쿠폰 코드를 입력하세요. 대소문자 무관, 공백 없이 입력");
        println("사용 가능: WELCOME10(10%, 최대 2,000원) / C500(500원) / FREE100(최대 10,000원 전액)");
        String code = readLine("쿠폰 코드: ").trim().toUpperCase();

        int discount = 0; // 할인액
        if ("WELCOME10".equals(code)) {
            discount = (amountDue * 10) / 100;
            if (discount > 2000) discount = 2000;
        } else if ("C500".equals(code)) {
            discount = 500;
        } else if ("FREE100".equals(code)) {
            discount = Math.min(amountDue, 10000);
        } else {
            return PaymentResult.fail("존재하지 않는 쿠폰입니다.");
        }

        if (discount <= 0) return PaymentResult.fail("할인 적용 금액이 없습니다.");
        if (discount > amountDue) discount = amountDue;

        int discounted = amountDue - discount; // 잔액
        println("\n쿠폰 적용됨: " + code);
        println("할인액  : " + money(discount));
        println("결제금액: " + money(discounted));

        if (discounted <= 0) return PaymentResult.ok(0, 0, "쿠폰만으로 전액 차감 성공");

        while (true) {
            println("\n잔액 결제수단: 1)현금  2)카드  0)취소");
            println("형식 안내: 현금=정수원화 예) 3000 / 카드=번호 16자리, 비밀번호 4자리");
            int sel = readInt("선택: ");
            PaymentResult r;
            if (sel == 1) {
                r = payCash(discounted);
            } else if (sel == 2) {
                r = payCard(discounted);
            } else if (sel == 0) {
                return PaymentResult.fail("잔액 결제 취소");
            } else {
                println("잘못된 선택입니다.");
                continue;
            }
            return r;
        }
    }

    // 숫자 16자리 검사
    private boolean is16Digits(String s) {
        if (s == null || s.length() != 16) return false;
        for (int i = 0; i < 16; i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    // 숫자 4자리 검사
    private boolean is4Digits(String s) {
        if (s == null || s.length() != 4) return false;
        for (int i = 0; i < 4; i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    // 출력/입력 유틸
    private void println(String s) { System.out.println(s); }
    private String money(int amount) { return String.format("%,d원", amount); }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 정수로 입력하세요. 예) 7000");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
