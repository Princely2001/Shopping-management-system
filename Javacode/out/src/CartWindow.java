import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartWindow extends JFrame {
    private List<CartItem> shoppingCart;
    private JLabel totalLabel;
    private JLabel discountLabel;
    private JLabel finalTotalLabel;

    public CartWindow(List<CartItem> shoppingCart,boolean isFirstPurchase) {
        this.shoppingCart = shoppingCart;
        setTitle("Shopping Cart");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Shopping Cart", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(headerLabel, BorderLayout.NORTH);

        // Table setup
        CartTableModel model = new CartTableModel();
        JTable table = new JTable(model);
        table.setRowMargin(5);
        table.setRowHeight(60); // Increase row height to accommodate multiple lines
        table.getColumnModel().getColumn(0).setCellRenderer(new MultilineCellRenderer()); // Set custom renderer for product column
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(10, 10));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Footer for totals
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Add paddingAdd padding

        double total = calculateTotal(shoppingCart);
        double categoryDiscount = calculateCategoryDiscount(shoppingCart);
        double firstPurchaseDiscount = isFirstPurchase ? total * 0.1 : 0;

        JLabel totalLabel = new JLabel(String.format("Total: $%.2f", total), JLabel.RIGHT);
        JLabel categoryDiscountLabel = new JLabel(String.format("Category Discount: $%.2f", categoryDiscount), JLabel.RIGHT);
        JLabel firstPurchaseDiscountLabel = new JLabel(String.format("First Purchase Discount: $%.2f", firstPurchaseDiscount), JLabel.RIGHT);
        JLabel finalTotalLabel = new JLabel(String.format("Final Total: $%.2f", (total - categoryDiscount - firstPurchaseDiscount)), JLabel.RIGHT);

        customizeLabel(totalLabel);
        customizeLabel(categoryDiscountLabel);
        customizeLabel(firstPurchaseDiscountLabel);
        customizeLabel(finalTotalLabel);

        // Apply bold font and right alignment
        applyLabelStyle(totalLabel);
        applyLabelStyle(categoryDiscountLabel);
        applyLabelStyle(firstPurchaseDiscountLabel);
        applyLabelStyle(finalTotalLabel);

        footerPanel.add(totalLabel);
        footerPanel.add(categoryDiscountLabel);
        footerPanel.add(firstPurchaseDiscountLabel);
        footerPanel.add(finalTotalLabel);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void customizeLabel(JLabel label) {
        // Increase font size
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 14));

        // Right alignment
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        // Add padding (top, left, bottom, right)
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    }


    private void applyLabelStyle(JLabel label) {
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }

    private double calculateCategoryDiscount(List<CartItem> cart) {
        double discount = 0.0;
        Map<String, Integer> categoryCounts = new HashMap<>();
        Map<String, Double> categoryPrices = new HashMap<>();

        // Count the number of products in each category and sum their prices
        for (CartItem item : cart) {
            String category = item.getProduct().getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + item.getQuantity());
            categoryPrices.put(category, categoryPrices.getOrDefault(category, 0.0) + item.getTotalPrice());
        }

        // Apply discount if a category has at least three products
        for (String category : categoryCounts.keySet()) {
            if (categoryCounts.get(category) >= 3) {
                discount += categoryPrices.get(category) * 0.20; // 20% discount
            }
        }

        return discount;
    }

    private double calculateTotal(List<CartItem> cart) {
        double total = 0;
        for (CartItem item : shoppingCart) {
            total += item.getTotalPrice();
        }
        return total;
    }

    private double calculateDiscount() {
        // Implement discount logic here
        return 0; // Placeholder
    }

    private double calculateFinalTotal() {
        // Implement final total calculation here
        return calculateTotal(shoppingCart) - calculateDiscount();
    }

    // Inner class for table model
    class CartTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Product", "Quantity", "Total Price"};

        @Override
        public int getRowCount() {
            return shoppingCart.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            CartItem item = shoppingCart.get(rowIndex);
            switch (columnIndex) {
                case 0: // Product column
                    Product product = item.getProduct();
                    String additionalInfo = "";

                    // Assuming additional info can be retrieved from product
                    if (product instanceof Electronics) {
                        Electronics electronics = (Electronics) product;
                        additionalInfo = electronics.getElec_brand() + ", " + electronics.getElec_warrenty_years();
                    } else if (product instanceof Clothing) {
                        Clothing clothing = (Clothing) product;
                        additionalInfo = clothing.getCloth_color() + ", " + clothing.getCloth_size();
                    }

                    return product.getProduct_id() + "\n" + product.getProduct_name() + "\n" + additionalInfo;
                case 1: // Quantity column
                    return item.getQuantity();
                case 2: // Total Price column
                    return item.getTotalPrice();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    class MultilineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultilineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

}

