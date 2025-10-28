import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class Worker {
    WareHouse wareHouse;
    public Worker(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }
    public Product getProductByOrder(Order order){

        Product product = getProductByName(order);
//        if(product != null) {
        var hashMap = wareHouse.getHashMap();
        Integer quantity =  hashMap.get(product);
        if(order.productQuantity() <= quantity) {
            hashMap.put(Objects.requireNonNull(product),product.quantity-quantity);
            product.setQuantity(product.quantity-quantity);
            System.out.println("Product returned");
            return product;
        }
        return null;

    }
    private  Product getProductByName(Order order) {
        List<Product> products = ProductCatalog.getProducts();
        Optional<Product> productMaybe = products.parallelStream()
                .filter(product -> product.getName().equals(order.productName()))
                .findFirst();
        if(productMaybe.isPresent()) {
            Product product =  productMaybe.get();
            if(product.getQuantity() >= order.productQuantity()) {
                return product;
            };
        }
        return null;

    }
    public Product takeOrderGetProduct(){
        BlockingQueue<Order> blockingQueue = OrderQueue.getOrderBlockingQueue();
        Product product = null;
        Callable<Product> callable = new Callable<>() {
            @Override
            public Product call() throws Exception {
                Order order = blockingQueue.take();
                return getProductByOrder(order);

            }
        };
        try {
            return callable.call();
        } catch (Exception e) {
            System.out.println("Order take mistake");
            throw new RuntimeException(e);
        }
    }
}
