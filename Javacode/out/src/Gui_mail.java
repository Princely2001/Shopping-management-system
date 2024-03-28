import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


class Gui_mail extends JFrame {
    JButton button;
    JComboBox comboBox;
    JLabel label;
    private static final WestminsterShoppingManager S_Manager = new WestminsterShoppingManager();
    private List<CartItem> shoppingCart;

    public Gui_mail() {
        shoppingCart = new ArrayList<>();

        setTitle("Westminster Shopping Centre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.cyan);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        // Load products from file
        S_Manager.loadProductsFromFile();

        // Product Table setup
        ProductTableModel model = new ProductTableModel(S_Manager.product_arr2);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);

        // Create and set a TableRowSorter for the table
        TableRowSorter<ProductTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // North Panel with Dropdown and Cart Button
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.setBackground(Color.lightGray);
        JLabel label = new JLabel("Select Product Category: ");
        String[] Shop_items = {"All", "Electronics", "Clothing"};
        JComboBox<String> comboBox = new JComboBox<>(Shop_items);
        comboBox.setBackground(Color.cyan);
        JButton cartButton = new JButton("Shopping Cart");
        cartButton.setBackground(Color.red);

        // Add ActionListener to the JComboBox
        comboBox.addActionListener(e -> {
            String selectedCategory = (String) comboBox.getSelectedItem();
            if ("All".equals(selectedCategory)) {
                sorter.setRowFilter(null); // Show all rows
            } else {
                // Filter rows based on the selected category
                sorter.setRowFilter(RowFilter.regexFilter(selectedCategory, 2)); // 2 is the column index for category
            }
        });

        // cart screen open
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartWindow cartWindow = new CartWindow(shoppingCart, true);
                cartWindow.setVisible(true);
            }
        });

        northPanel.add(label);
        northPanel.add(comboBox);
        northPanel.add(cartButton);
        add(northPanel, BorderLayout.NORTH);

        // Define a common background color
        Color backgroundColor = Color.lightGray;

        // South Panel for Product Details
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setBackground(Color.lightGray);
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Heading Panel
        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.setBackground(backgroundColor);
        JLabel headingLabel = new JLabel("Selected Product Details");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingPanel.add(headingLabel);
        southPanel.add(headingPanel);

        // Detail Panels
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(backgroundColor);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(backgroundColor);

        // Add ListSelectionListener to table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int Row = table.getSelectedRow();
                if (Row >= 0) {
                    leftPanel.removeAll(); // Clear previous details
                    rightPanel.removeAll();

                    // Fetch the product directly from the list using the selected row index
                    Product selected = S_Manager.product_arr2.get(Row);

                    // Get the category and info string from the selected product
                    String category = selected.getCategory();
                    int numAvailable = selected.getNum_ava_items(); // Fetch Num_ava_items from the product

                    // Add common details to left panel
                    addDetailToPanel(leftPanel, "Product ID", selected.getProduct_id());
                    addDetailToPanel(leftPanel, "Name", selected.getProduct_name());
                    addDetailToPanel(leftPanel, "Category", category);
                    addDetailToPanel(leftPanel, "Price", String.valueOf(selected.getprice()));
                    addDetailToPanel(leftPanel, "Available Items", String.valueOf(numAvailable)); // Displaying Num_ava_items

                    // Additional info based on the category
                    if (category.equalsIgnoreCase("Electronics") && selected instanceof Electronics) {
                        Electronics electronicItem = (Electronics) selected;
                        addDetailToPanel(rightPanel, "Brand", electronicItem.getElec_brand());
                        addDetailToPanel(rightPanel, "Warranty", String.valueOf(electronicItem.getElec_warrenty_years()));
                    } else if (category.equalsIgnoreCase("Clothing") && selected instanceof Clothing) {
                        Clothing clothingItem = (Clothing) selected;
                        addDetailToPanel(rightPanel, "Color", clothingItem.getCloth_color());
                        addDetailToPanel(rightPanel, "Size", clothingItem.getCloth_size());
                    }

                    southPanel.add(leftPanel);
                    southPanel.add(rightPanel);

                    southPanel.revalidate(); // Refresh panel
                    southPanel.repaint();
                }
            }
        });

        // Add detail panels to southPanel
        southPanel.add(leftPanel);
        southPanel.add(rightPanel);

        add(southPanel, BorderLayout.CENTER);

        // Add JScrollPane with JTable to the center of the layout
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Separate Panel for 'Add to Cart' button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.lightGray);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(addToCartButton);

        // Panel to combine southPanel and buttonPanel
        JPanel combinedSouthPanel = new JPanel();
        combinedSouthPanel.setLayout(new BorderLayout());
        combinedSouthPanel.add(southPanel, BorderLayout.CENTER);
        combinedSouthPanel.add(buttonPanel, BorderLayout.PAGE_END);

        // Add combined panel to the bottom of the frame
        add(combinedSouthPanel, BorderLayout.PAGE_END);

        // Set up the "Add to Cart" button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Product selectedProduct = S_Manager.product_arr2.get(selectedRow);

                    // Check if the product is already in the cart
                    boolean productFound = false;
                    for (CartItem item : shoppingCart) {
                        if (item.getProduct().equals(selectedProduct)) {
                            // Increase the quantity by 1 if the product is already in the cart
                            int newQuantity = item.getQuantity() + 1;
                            item.setQuantity(newQuantity);
                            productFound = true;
                            System.out.println("Increased quantity of product: " + selectedProduct.getProduct_name());
                            break;
                        }
                    }

                    // If the product is not in the cart, add it as a new item
                    if (!productFound) {
                        CartItem newItem = new CartItem(selectedProduct, 1);
                        shoppingCart.add(newItem);
                        System.out.println("Added to cart: " + newItem);
                    }

                    // Optionally, refresh the cart view to reflect the changes
                }
            }
        });
    }

    private void addDetailToPanel(JPanel panel, String label, Object value) {
        String valueStr;
        if (value instanceof Double) {
            valueStr = String.format("%.2f", value);  // Format for double values
        } else if (value instanceof Integer) {
            valueStr = Integer.toString((Integer) value);  // Convert integer to string
        } else {
            valueStr = value.toString();  // Default to toString for other types
        }

        JLabel detailLabel = new JLabel(label + ": " + valueStr, SwingConstants.CENTER);
        detailLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Increase font size
        detailLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Padding between rows
        panel.add(detailLabel);
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Gui_mail frame = new Gui_mail();
            frame.setVisible(true);
        });
    }
}