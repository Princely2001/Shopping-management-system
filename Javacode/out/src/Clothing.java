public class Clothing extends Product{
    private String cloth_color;
    private String cloth_size;

    //Parameterized Constructor
    public Clothing(String Product_id,String Product_name,int Num_ava_items,String Category,double price,String cloth_color,String cloth_size){
        super(Product_id,Product_name,Num_ava_items,Category,price);
        this.cloth_color = cloth_color;
        this.cloth_size = cloth_size;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "Product_id = '" + getProduct_id() + '\'' +
                ", Product_name = '" + getProduct_name() + '\'' +
                ", Num_ava_items = " + getNum_ava_items() +
                ", price = " + getprice() + ", Colour = '" +  cloth_color + '\'' +  " , Size  = '" + cloth_size + '\'' +
                '}';
    }
    //getters and setters
    public String getCloth_color() {
        return cloth_color;
    }

    public String getCloth_size() {
        return cloth_size;
    }

    public void setCloth_color(String cloth_color) {
        this.cloth_color = cloth_color;
    }

    public void setCloth_size(String cloth_size) {
        this.cloth_size = cloth_size;
    }
}
