import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingApp {
    private ShoppingCart cart;
    private JFrame frame;
    private Map<JCheckBox, Item> itemCheckboxes;
    private JTextArea cartArea;

    public ShoppingApp() {
        cart = new ShoppingCart();
        frame = new JFrame("ShoppingApp");

        // Define grocery items
        String[] groceryItems = {"Ham", "Apples", "Bananas", "Cereal", "Milk",
                "Bread", "Orange Juice", "Eggs", "Cheese", "Rice"};

        // Create checkbox for each item
        itemCheckboxes = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            JCheckBox checkBox = new JCheckBox(groceryItems[i]);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Item selectedItem = itemCheckboxes.get(checkBox);
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        cart.addItemOrder(new ItemOrder(selectedItem, 1));
                    } else {
                        cart.removeItemOrder(selectedItem);
                    }
                    updateCart();
                }
            });
            itemCheckboxes.put(checkBox, new Item(groceryItems[i], 3));
            itemCheckboxes.put(new JCheckBox("Box of " + groceryItems[i]), new Item("Box of " + groceryItems[i], 4));
        }

        cartArea = new JTextArea();
        cartArea.setEditable(false);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        for (JCheckBox checkBox : itemCheckboxes.keySet()) {
            frame.add(checkBox);
        }
        frame.add(new JScrollPane(cartArea));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setVisible(true);
    }

    private void updateCart() {
        StringBuilder sb = new StringBuilder();
        double total = cart.calculateTotal();

        for (ItemOrder order : cart.getOrders()) {
            sb.append(order.getItem().getName()).append(": $").append(order.getOrderTotal()).append("\n");
        }

        sb.append("-------\n").append("Total: $").append(total);
        cartArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShoppingApp::new);
    }
}