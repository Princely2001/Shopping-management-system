
import jdk.jfr.Category;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileReader;

import static java.util.Objects.*;


public class WestminsterShoppingManager implements ShoppingManager {
    @Override
    public String toString() {
        return "WestminsterShoppingManager{" +
                "product_arr2=" + product_arr2 +
                '}';
    }

    public static void main(String[] args) {

        WestminsterShoppingManager shop_manager = new WestminsterShoppingManager();
        while (true) {
            //Print option lists
            System.out.println("--------------------------------------------------");
            System.out.println("Welcome to the Shop");
            System.out.println("--------------------------------------------------");
            System.out.println("Please select an option:");
            System.out.println("1) Add new item");
            System.out.println("2) Remove item");
            System.out.println("3) Show list");
            System.out.println("4) Save to file");
            System.out.println("5) Load file");
            System.out.println("6) Launch GUI");
            System.out.println("     0) Quit");
            System.out.println("--------------------------------------------------");
            //Option input from user
            Scanner option = new Scanner(System.in);
            System.out.println("Please enter your prefered option");
            int option1 = option.nextInt();
            //Check user input options

            if (Objects.equals(option1, 1)) {
                if (shop_manager.product_arr2.size() <= 50) {
                    shop_manager.add_Product();
                } else {
                    System.out.println("You can't add another items");
                }
            } else if (Objects.equals(option1, 2)) {
                shop_manager.remove_Product();

            } else if (Objects.equals(option1, 3)) {
                shop_manager.product_list();

            } else if (Objects.equals(option1, 4)) {
                shop_manager.save_file();
            } else if (Objects.equals(option1, 5)) {
                shop_manager.load_file();
            } else if (Objects.equals(option1, 6)) {
                // Launch the GUI
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new Gui_mail().setVisible(true);
                    }
                });
            }
            else if (Objects.equals(option1, 0)) {
                System.out.println("Thank you visit again");
                System.exit(0);
            } else {
                System.out.println("Incorrect option please enter 0 to 5 values ");
            }
        }
    }

    public void loadProductsFromFile() {
        String filePath = "src/shop_cart.csv";
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7) {
                    String productId = values[0];
                    String productName = values[1];
                    int numberAvailable = Integer.parseInt(values[2]);
                    String category = values[3];
                    double price = Double.parseDouble(values[4]);
                    String additionalInfo1 = values[5];
                    String additionalInfo2 = values[6];

                    if (category.equalsIgnoreCase("Electronics")) {
                        Electronics electronicItem = new Electronics(productId, productName, numberAvailable, category, price, additionalInfo1, Integer.parseInt(additionalInfo2));
                        product_arr2.add(electronicItem);
                    } else if (category.equalsIgnoreCase("Clothing")) {
                        Clothing clothingItem = new Clothing(productId, productName, numberAvailable, category, price, additionalInfo1, additionalInfo2);
                        product_arr2.add(clothingItem);
                    }
                }
            }
            System.out.println("Products loaded successfully from the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file: " + e.getMessage());
        }
    }

    ArrayList<Product> product_arr2;

    public WestminsterShoppingManager() {
        this.product_arr2 = new ArrayList<Product>();
    }

    @Override
    public void add_Product() {//add products to the system
        try {
            Scanner add_product = new Scanner(System.in);
            System.out.println("Please Enter item type Clothing or Electronics ");
            String new_product = add_product.next().toLowerCase();
            if (new_product.equals("electronics")) {
                System.out.println("Please Enter product name");
                String Product_name = add_product.next();
                System.out.println("Please Enter number of products available");
                int Num_ava_items = add_product.nextInt();
                System.out.println("Please Enter Product Category");
                String Category = add_product.next();
                System.out.println("Please Enter item price");
                double price = add_product.nextDouble();
                System.out.println("Please Enter brand name of the electronic item");
                String Elec_brand = add_product.next();
                System.out.println("Please Enter the warrenty period in months ");
                int Elec_Warrenty = add_product.nextInt();
                Electronics Electric_item = new Electronics("", Product_name, Num_ava_items, Category, price, Elec_brand, Elec_Warrenty);
                product_arr2.add(Electric_item);
                System.out.println("Electronics item added successfully");
            } else if (new_product.equals("clothing")) {
                System.out.println("Please Enter product id");
                String Product_id = add_product.next();
                System.out.println("Please Enter product name");
                String Product_name = add_product.next();
                System.out.println("Please Enter number of products available");
                int Num_ava_items = add_product.nextInt();
                System.out.println("Please Enter Product Category");
                String Category = add_product.next();
                System.out.println("Please Enter item price");
                double price = add_product.nextDouble();
                System.out.println("Please Enter cloth color");
                String cloth_color = add_product.next();
                System.out.println("Please Enter cloth size");
                String cloth_size = add_product.next();
                Clothing Cloth_item = new Clothing("", Product_name, Num_ava_items, Category, price, cloth_color, cloth_size);
                product_arr2.add(Cloth_item);
                System.out.println("Clothing item added successfully");
            } else {
                System.out.println("Please Enter Electronics or Clothing");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void remove_Product() {//remove items using product id
        try {
            Scanner pro_remove = new Scanner(System.in);
            System.out.println("Please enter product id of removable item ");
            String remove_id = pro_remove.next();
            for (int i = 0; i < product_arr2.size(); i++) {
                if (Objects.equals(product_arr2.get(i).getProduct_id(), remove_id)) {
                    if (Objects.equals(product_arr2.get(i).getCategory(), "Electronics")) {
                        System.out.println("Removed item details : " + " Product_id : " + product_arr2.get(i).getProduct_id() +
                                " Product_name :  " + product_arr2.get(i).getProduct_name() + "  Product_Category :  " + product_arr2.get(i).getCategory());
                        product_arr2.remove(i);
                        System.out.println("Electrinics Product removed Successfully");
                    } else if (Objects.equals(product_arr2.get(i).getCategory(), "Clothing")) {
                        System.out.println("Removed item details : " + " Product_id : " + product_arr2.get(i).getProduct_id() +
                                " Product_name :  " + product_arr2.get(i).getProduct_name() + "  Product_Category :  " + product_arr2.get(i).getCategory());
                        product_arr2.remove(i);
                        System.out.println("Clothing Product removed Successfully");
                    } else {
                        System.out.println("Please Enter valid product id");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void product_list() {//Show product list
        try {
            System.out.println(" ******  Electronics product Items List ****** ");
            System.out.println(" ********************************************* ");
            for (int i = 0; i < product_arr2.size(); i++) {
                if (Objects.equals(product_arr2.get(i).getCategory(), "Electronics")) {
                    Collections.sort(product_arr2, (p1, p2) -> p1.getProduct_id().compareTo(p2.getProduct_id()));
                    System.out.println(product_arr2.get(i));
                }
            }
            System.out.println(" ****** Clothing product Items List **********");
            System.out.println(" ********************************************* ");
            for (int i = 0; i < product_arr2.size(); i++) {
                if (Objects.equals(product_arr2.get(i).getCategory(), "Clothing")) {
                    Collections.sort(product_arr2, (p1, p2) -> p1.getProduct_id().compareTo(p2.getProduct_id()));
                    System.out.println(product_arr2.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void load_file() {//load items form file
        try (BufferedReader shop_cart = new BufferedReader(new FileReader("src/shop_cart.csv"))) {
            String read = shop_cart.readLine(); // Read header line

            while ((read = shop_cart.readLine()) != null) {
                String[] values = read.split(",");
                if (values.length >= 7) {
                    String productId = values[0];
                    String productName = values[1];
                    String numberAvailable = values[2];
                    String category = values[3];
                    String price = values[4];
                    String detail1 = values[5];
                    String detail2 = values[6];

                    if (category.equalsIgnoreCase("Electronics")) {
                        String brand = detail1;
                        String warranty = detail2;
                        System.out.printf("ID: %s, Name: %s, Available: %s, Category: %s, Price: %s, Brand: %s, Warranty: %s%n",
                                productId, productName, numberAvailable, category, price, brand, warranty);
                    } else {
                        String color = detail1;
                        String size = detail2;
                        System.out.printf("ID: %s, Name: %s, Available: %s, Category: %s, Price: %s, Color: %s, Size: %s%n",
                                productId, productName, numberAvailable, category, price, color, size);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file: " + e.getMessage());
        }
    }

    @Override
    public void save_file() {//products details save to the file
        String filePath = "src/shop_cart.csv";
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Check if file is new or empty, then write headers
            if (!file.exists() || file.length() == 0) {
                writer.write("ProductID,ProductName,NumberAvailable,Category,Price,AdditionalInfo1,AdditionalInfo2");
                writer.newLine();
            }

            for (Product p : product_arr2) {
                // Assign a new ID to the product
                String newId = "P" + getNextProductID();
                p.setProduct_id(newId);
                String line = formatProductAsCSV(p);
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Data appended to file successfully.");

            // Clear the product list after saving
            product_arr2.clear();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }

    private String formatProductAsCSV(Product product) {//create CSV format
        String additionalInfo1 = "";
        String additionalInfo2 = "";

        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            additionalInfo1 = electronics.getElec_brand();
            additionalInfo2 = String.valueOf(electronics.getElec_warrenty_years());
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            additionalInfo1 = clothing.getCloth_color();
            additionalInfo2 = clothing.getCloth_size();
        }

        return String.join(",", product.getProduct_id(), product.getProduct_name(),
                String.valueOf(product.getNum_ava_items()), product.getCategory(),
                String.valueOf(product.getprice()), additionalInfo1, additionalInfo2);
    }

    private static int getNextProductID() {//Create unique product id for the system
        int maxId = 0;
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/shop_cart.csv"))) {
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].startsWith("P")) {
                    try {
                        int id = Integer.parseInt(values[0].substring(1)); // Extract numeric part
                        maxId = Math.max(maxId, id);
                    } catch (NumberFormatException e) {
                        // If it's not a number, ignore and continue
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return maxId + 1; // Return the next ID
    }

}

