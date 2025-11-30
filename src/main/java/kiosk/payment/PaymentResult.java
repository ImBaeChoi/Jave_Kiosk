package kiosk.payment;

// 결제 결과 전달용 DTO
public final class PaymentResult {
    public final boolean success; // 결제 성공 여부
    public final int paid;        // 실제 지불 금액
    public final int change;      // 거스름돈
    public final String message;  // 메시지

    public PaymentResult(boolean success, int paid, int change, String message) {
        this.success = success;
        this.paid = paid;
        this.change = change;
        this.message = message;
    }

    public static PaymentResult ok(int paid, int change, String message) {
        return new PaymentResult(true, paid, change, message);
    }

    public static PaymentResult fail(String message) {
        return new PaymentResult(false, 0, 0, message);
    }
}
