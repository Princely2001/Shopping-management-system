import java.util.ArrayList;
import java.util.List;

public class Electronics extends Product {

    private String Elec_brand;
    private int Elec_warrenty;

   //Parameterised constructor
    public Electronics(String Product_id,String Product_name,int Num_ava_items,String Category,double price,String Elec_brand,int Elec_Warrenty){
       super(Product_id,Product_name,Num_ava_items,Category,price);
        this.Elec_brand = Elec_brand;
        this.Elec_warrenty = Elec_Warrenty;
    }
    @Override
    public String toString() {
        return "Electronics{" +
                "Product_id = '" + getProduct_id() + '\'' +
                ", Product_name = '" + getProduct_name() + '\'' +
                ", Num_ava_items = " + getNum_ava_items() +
                ", price = " + getprice() + ", Product_Brand = '" +  Elec_brand + '\'' +  ", Warranty_Period = '" + Elec_warrenty + '\'' +
                '}';
    }

    public String getElec_brand() {
        return Elec_brand;
    }

    public int getElec_warrenty_years() {
        return Elec_warrenty;
    }

    public void setElec_brand(String elec_brand) {
        this.Elec_brand = elec_brand;
    }

    public void setElec_warrenty(int elec_warrenty) {
        this.Elec_warrenty = elec_warrenty;
    }
}
