import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductCatalog {
    private static  List<Product> products = DemoRunner.generateProducts();

    public static List<Product> getProducts(){
        return products;
    }

}
