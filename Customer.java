import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class Customer {

    public void createOrder(Order order){
        BlockingQueue<Order> queue = OrderQueue.getOrderBlockingQueue();

        new Runnable() {
            @Override
            public void run() {
                try {
                    queue.put(order);
                } catch (InterruptedException e) {
                    System.out.println("Order exception");
                    throw new RuntimeException(e);
                }
            }
        };

    }
}
