public abstract class Product {
    @Override
    public String toString() {
        return "Product{" +
                "Product_id='" + Product_id + '\'' +
                ", Product_name='" + Product_name + '\'' +
                ", Num_ava_items=" + Num_ava_items +
                ", price=" + price +
                '}';
    }
    private String Product_id;
    private String Product_name;
    private int Num_ava_items;
    private String Category;
    private double price;
    //Parameterized Constructor
    public Product(String Product_id,String Product_name,int Num_ava_items,String Category,double price){
        this.Product_id = Product_id;
        this.Product_name = Product_name;
        this.Num_ava_items = Num_ava_items;
        this.Category = Category;
        this.price = price;

    }
    //getters and setters
    public String getProduct_id() {
        return Product_id;
    }
    public String getProduct_name() {
        return Product_name;
    }
    public int getNum_ava_items() {
        return Num_ava_items;
    }
    public String getCategory(){return Category;}
    public double getprice(){return price;}

    public void setProduct_id(String Product_id) {
        this.Product_id = Product_id;
    }
    public void setProduct_name(String Product_name) {
        this.Product_name = Product_name;
    }
    public void setNum_ava_items(int Num_ava_items) {
        this.Num_ava_items = Num_ava_items;
    }

    public void setCategory(String Category) {this.Category = Category;}

    public void setPrice(double price) {
        this.price = price;
    }
}
