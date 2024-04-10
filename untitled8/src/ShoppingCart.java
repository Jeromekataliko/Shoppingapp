import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ItemOrder> orders;

    public ShoppingCart() {
        this.orders = new ArrayList<>();
    }

    public void addItemOrder(ItemOrder order) {
        orders.add(order);
    }

    public void removeItemOrder(Item item) {
        orders.removeIf(order -> order.getItem().equals(item));
    }

    public List<ItemOrder> getOrders() {
        return orders;
    }

    public double calculateTotal() {
        double total = orders.stream().mapToDouble(ItemOrder::getOrderTotal).sum();
        if (total > 10) {
            return total * 0.9; // Apply a 10% discount
        }
        return total;
    }
}
