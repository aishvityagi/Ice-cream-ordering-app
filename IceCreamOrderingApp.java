import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IceCreamOrderingApp extends JFrame {

    // Variables for selection
    private JComboBox<String> categoryComboBox;
    private JComboBox<Integer> scoopsComboBox;
    private JComboBox<String> flavoursComboBox[];
    private JComboBox<String> sundaeComboBox;
    private JComboBox<String> drinkComboBox;

    private JTextArea orderSummary;
    private JLabel totalPriceLabel;
    private double totalPrice = 0.0;

    // Prices
    private final double scoopPrice = 2.0;
    private final double sundaePrice[] = {3.0, 5.0, 7.0}; // Single, Double, Triple
    private final double drinkPrice[] = {1.5, 2.0, 2.5}; // Small, Medium, Large

    // Tax Rates
    private final double gstRate = 0.05; // 5% GST
    private final double cgstRate = 0.05; // 5% CGST
    private final double sgstRate = 0.05; // 5% SGST

    // Stores multiple orders
    private ArrayList<String> orderList;
    private ArrayList<Double> priceList;

    public IceCreamOrderingApp() {
        orderList = new ArrayList<>();
        priceList = new ArrayList<>();

        setTitle("Ice Cream Ordering App");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Welcome to the Ice Cream Shop", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // Select Category (Cone, Sundaes, Drinks)
        mainPanel.add(new JLabel("Select Category:"));
        String[] categories = {"Cone", "Sundaes", "Cold Drinks"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.addActionListener(new CategoryActionListener());
        mainPanel.add(categoryComboBox);

        // Cone Options (Scoops and Flavours)
        mainPanel.add(new JLabel("Select Number of Scoops:"));
        scoopsComboBox = new JComboBox<>(new Integer[]{1, 2, 3});
        scoopsComboBox.setEnabled(false);
        mainPanel.add(scoopsComboBox);

        mainPanel.add(new JLabel("Select Flavours:"));
        flavoursComboBox = new JComboBox[3];
        for (int i = 0; i < 3; i++) {
            flavoursComboBox[i] = new JComboBox<>(new String[]{"Vanilla", "Chocolate", "Strawberry", "Mango", "Butterscotch"});
            flavoursComboBox[i].setEnabled(false);
            mainPanel.add(flavoursComboBox[i]);
        }

        // Sundae Options
        mainPanel.add(new JLabel("Select Sundae Size:"));
        sundaeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Triple"});
        sundaeComboBox.setEnabled(false);
        mainPanel.add(sundaeComboBox);

        // Drink Options
        mainPanel.add(new JLabel("Select Cold Drink:"));
        drinkComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        drinkComboBox.setEnabled(false);
        mainPanel.add(drinkComboBox);

        // Order Summary and Total Price
        mainPanel.add(new JLabel("Order Summary:"));
        orderSummary = new JTextArea(5, 20);
        orderSummary.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        mainPanel.add(scrollPane);

        mainPanel.add(new JLabel("Total Price:"));
        totalPriceLabel = new JLabel("$0.00");
        mainPanel.add(totalPriceLabel);

        // Add Item Button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new AddItemButtonListener());
        mainPanel.add(addItemButton);

        // Submit Button
        JButton submitButton = new JButton("Place Order");
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Action listener for category selection
    private class CategoryActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            if (selectedCategory.equals("Cone")) {
                scoopsComboBox.setEnabled(true);
                for (int i = 0; i < 3; i++) {
                    flavoursComboBox[i].setEnabled(true);
                }
                sundaeComboBox.setEnabled(false);
                drinkComboBox.setEnabled(false);
            } else if (selectedCategory.equals("Sundaes")) {
                scoopsComboBox.setEnabled(false);
                for (int i = 0; i < 3; i++) {
                    flavoursComboBox[i].setEnabled(false);
                }
                sundaeComboBox.setEnabled(true);
                drinkComboBox.setEnabled(false);
            } else if (selectedCategory.equals("Cold Drinks")) {
                scoopsComboBox.setEnabled(false);
                for (int i = 0; i < 3; i++) {
                    flavoursComboBox[i].setEnabled(false);
                }
                sundaeComboBox.setEnabled(false);
                drinkComboBox.setEnabled(true);
            }
        }
    }

    // Action listener for adding the item to the order
    private class AddItemButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            StringBuilder item = new StringBuilder();
            double price = 0.0;
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            if (selectedCategory.equals("Cone")) {
                int scoops = (int) scoopsComboBox.getSelectedItem();
                item.append("Ice Cream Cone with ").append(scoops).append(" scoop(s)\n");
                price += scoops * scoopPrice;
                for (int i = 0; i < scoops; i++) {
                    String flavour = (String) flavoursComboBox[i].getSelectedItem();
                    item.append("Flavour ").append(i + 1).append(": ").append(flavour).append("\n");
                }
            } else if (selectedCategory.equals("Sundaes")) {
                String sundaeSize = (String) sundaeComboBox.getSelectedItem();
                item.append(sundaeSize).append(" Sundae\n");
                switch (sundaeSize) {
                    case "Single":
                        price += sundaePrice[0];
                        break;
                    case "Double":
                        price += sundaePrice[1];
                        break;
                    case "Triple":
                        price += sundaePrice[2];
                        break;
                }
            } else if (selectedCategory.equals("Cold Drinks")) {
                String drinkSize = (String) drinkComboBox.getSelectedItem();
                item.append(drinkSize).append(" Cold Drink\n");
                switch (drinkSize) {
                    case "Small":
                        price += drinkPrice[0];
                        break;
                    case "Medium":
                        price += drinkPrice[1];
                        break;
                    case "Large":
                        price += drinkPrice[2];
                        break;
                }
            }

            if (item.length() > 0) {
                orderList.add(item.toString());
                priceList.add(price);
                totalPrice += price;

                // Update the GUI order summary
                updateOrderSummary();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item.");
            }
        }
    }

    // Action listener for submitting the order
    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double gst = totalPrice * gstRate;
            double cgst = totalPrice * cgstRate;
            double sgst = totalPrice * sgstRate;
            double totalWithTax = totalPrice + gst + cgst + sgst;

            // Prepare bill content
            StringBuilder bill = new StringBuilder();
            bill.append("************ Ice Cream Shop ************\n\n");
            bill.append("Order Details:\n");
            for (int i = 0; i < orderList.size(); i++) {
                bill.append(orderList.get(i)).append("\n");
            }
            bill.append("\nSubtotal: $").append(String.format("%.2f", totalPrice)).append("\n");
            bill.append("GST (5%): $").append(String.format("%.2f", gst)).append("\n");
            bill.append("CGST (5%): $").append(String.format("%.2f", cgst)).append("\n");
            bill.append("SGST (5%): $").append(String.format("%.2f", sgst)).append("\n");
            bill.append("--------------------------------\n");
            bill.append("Total: $").append(String.format("%.2f", totalWithTax)).append("\n");
            bill.append("********************************\n");

            // Show the bill in the GUI
            orderSummary.append("\n\nFinal Bill:\n");
            orderSummary.append(bill.toString());

            // Show the bill in a separate window
            JFrame billFrame = new JFrame("Bill Preview");
            billFrame.setSize(400, 600);
            JTextArea billTextArea = new JTextArea();
            billTextArea.setText(bill.toString());
            billTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
            billTextArea.setEditable(false);
            JScrollPane billScrollPane = new JScrollPane(billTextArea);
            billFrame.add(billScrollPane);
            billFrame.setVisible(true);
        }
    }

    // Update the order summary in the GUI
    private void updateOrderSummary() {
        orderSummary.setText("");  // Clear the text area first
        for (String item : orderList) {
            orderSummary.append(item);  // Add the item
            orderSummary.append("\n");  // Add a newline
        }
        totalPriceLabel.setText("$" + String.format("%.2f", totalPrice));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IceCreamOrderingApp::new);
    }
}
