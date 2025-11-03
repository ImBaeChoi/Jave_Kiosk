package Cart;

public class Quantity_Calculation extends Cart_CRUD {
    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }
}
