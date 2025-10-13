package Cart;

public class Price_Calculation extends Menu_CRUD {
    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}