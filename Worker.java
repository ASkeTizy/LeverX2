import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

public class Worker implements Runnable{
    WareHouse wareHouse;
    private  final BlockingQueue<Order> orderBlockingQueue= OrderQueue.getOrderBlockingQueue();
    public Worker(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    public Product getProductByOrder(Order order) {

        Product product = getProductByName(order);
//        if(product != null) {
        var hashMap = wareHouse.getHashMap();
        Integer quantity = hashMap.get(product);
        if (order.productQuantity() <= quantity) {
            hashMap.put(Objects.requireNonNull(product), quantity - order.productQuantity());
            product.setQuantity(product.getQuantity() - order.productQuantity());
            System.out.println("Product returned");
            return product;
        }
        return null;

    }

    private Product getProductByName(Order order) {
        List<Product> products = ProductCatalog.getProducts();
        Optional<Product> productMaybe = products.parallelStream()
                .filter(product -> product.getName().equals(order.productName()))
                .findFirst();
        if (productMaybe.isPresent()) {
            Product product = productMaybe.get();
            if (product.getQuantity() >= order.productQuantity()) {
                return product;
            }
            ;
        }
        return null;

    }

    public Worker takeOrderGetProduct() {
     return this;
    }

    @Override
    public void run() {
        try {
            if(!orderBlockingQueue.isEmpty()) {
                Order order = orderBlockingQueue.take();
                Product product = getProductByOrder(order);

                if (product != null) {
                    var orders = wareHouse.getOrders();
                    orders.add(order);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
