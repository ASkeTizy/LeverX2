import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

public class Worker implements Runnable{
    WareHouse wareHouse;

    BlockingQueue<Order> orderBlockingQueue = OrderQueue.getOrderBlockingQueue();


    public Worker(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }
    public Product getProductByOrder(Order order) {

        Product product = getProductByName(order);

        var hashMap = wareHouse.getHashMap();
        synchronized (this) {

            Integer quantity = hashMap.get(product);
            if (order.productQuantity() <= quantity) {
                hashMap.put(Objects.requireNonNull(product), quantity - order.productQuantity());
                product.setQuantity(product.getQuantity() - order.productQuantity());
                System.out.println("Product returned");
                return product;
            }
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
            var reservation = getReservationByProduct(product);
            if (product.getQuantity() >= order.productQuantity() + reservation.productQuantity()) {
                return product;
            }

        }
        return null;

    }
    private Reservation getReservationByProduct(Product product) {
        var reservations = ReservationOrder.getReservations();
        return reservations.stream()
                .filter(reservation -> product.getName().equals(reservation.productName()))
                .filter(reservation -> product.getQuantity() <= reservation.productQuantity())
                .findFirst().orElse(new Reservation("",0));
    }
    @Override
    public void run() {
        try {
            while (!orderBlockingQueue.isEmpty()) {

                synchronized (this) {


                    Order order = orderBlockingQueue.take();
                    Product product = getProductByOrder(order);

                    if (product != null) {
                        var orders = wareHouse.getOrders();
                        orders.add(order);
                    }

                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
