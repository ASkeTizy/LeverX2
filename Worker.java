import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class Worker implements Runnable {
    WareHouse wareHouse;

    BlockingQueue<Reservation> orderBlockingQueue = ReservationQueue.getOrderBlockingQueue();


    public Worker(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }


    @Override
    public void run() {
        try {
            while (!orderBlockingQueue.isEmpty()) {

                synchronized (this) {
                    Reservation reservation = orderBlockingQueue.take();
                    var productMaybe = findProduct(reservation);
                    reserveProduct(productMaybe, reservation);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void reserveProduct(Optional<Product> productMaybe, Reservation reservation) {
        try {

            if (productMaybe.isPresent()) {
                var product = productMaybe.get();
                var storedQuantity = wareHouse.getHashMap().get(product);
                var reservedProducts = wareHouse.getReservedProducts();
                var reservedQuantity = reservedProducts.get(product);
                if (reservedQuantity == null) {
                    reservedQuantity = 0;
                }
                if (storedQuantity >= reservation.quantity() + reservedQuantity) {
                    reservedProducts.put(product, reservation.quantity() + reservedQuantity);
                } else {
                    throw new IllegalArgumentException("Cannot be more products than in stock");
                }
            } else {
                throw new IllegalArgumentException("NO such product");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private Optional<Product> findProduct(Reservation reservation) {
        List<Product> products = ProductCatalog.getProducts();
        return products.parallelStream()
                .filter(product -> product.getName().equals(reservation.productName()))
                .findFirst();
//        if()
    }

}


