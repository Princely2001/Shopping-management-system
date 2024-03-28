import java.io.*;
import java.util.ArrayList;

public class CSVReader {
    public static ArrayList<Product> readProductsFromFile(String filePath) {
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Create instances based on the category
                if (values[3].equalsIgnoreCase("Electronics")) {
                    products.add(new Electronics(values[0], values[1], Integer.parseInt(values[2]), values[3], Double.parseDouble(values[4]), values[5], Integer.parseInt(values[6])));
                } else if (values[3].equalsIgnoreCase("Clothing")) {
                    products.add(new Clothing(values[0], values[1], Integer.parseInt(values[2]), values[3], Double.parseDouble(values[4]), values[5], values[6]));
                }
                // Ignore lines that do not match Electronics or Clothing
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
