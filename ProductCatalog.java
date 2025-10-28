import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductCatalog {
    private static  List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("Lenovo",3,2300.45));
        products.add(new Product("Macbook",6,3300.00));
        products.add(new Product("Philips monitor",10,300.35));
    }
    public static List<Product> getProducts(){
        return products;
    }

}
