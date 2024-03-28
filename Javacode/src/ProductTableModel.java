import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;




    // creating a custom table model
    public class ProductTableModel extends AbstractTableModel   {


        private final ArrayList<Product> product_arr3;

        private final String[] columnNames = {"Product_ID", "Name","Category","Price", "Info"};

        public ProductTableModel(ArrayList<Product> product_arr3) {
            this.product_arr3 = product_arr3;
        }

        @Override
        public int getRowCount() {
            return product_arr3.size();
        }


        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Product product = product_arr3.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return product.getProduct_id();
                case 1:
                    return product.getProduct_name();
                case 2:
                    return product.getCategory();
                case 3:
                    return product.getprice();
                case 4:
                    if (product.getCategory().equals("Electronics")) {
                        Electronics electronicsProduct = (Electronics) product;
                        return electronicsProduct.getElec_brand() + ", " + electronicsProduct.getElec_warrenty_years();
                    } else if (product.getCategory().equals("Clothing")) {
                        Clothing clothingProduct = (Clothing) product;
                        return clothingProduct.getCloth_color() + ", " + clothingProduct.getCloth_size();
                    } else {
                        return null;
                    }
                default:
                    return null;
            }
        }
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }



