import java.util.ArrayList;
import java.util.List;

 class Shopping_cart {
    public List<Product> products_arr;
    public Shopping_cart(){
        this.products_arr = new ArrayList<>();
    }
    public void add_Product(Product product){
        products_arr.add(product);
    }
    public void remove_Products(Product product){
        products_arr.remove(product);
    }
    public double Totalcost(){
        double total_cost = 0;
        for(Product product : products_arr){
            total_cost += product.getprice();
        }
        return total_cost;
    }


}
