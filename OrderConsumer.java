import java.util.List;
import java.util.concurrent.BlockingQueue;

public class OrderConsumer implements Runnable{
    BlockingQueue<Order> queue = OrderQueue.getOrderBlockingQueue();
    Order order;
    public OrderConsumer( Order order) {

        this.order = order;
    }

    public BlockingQueue<Order> getQueue() {
        return queue;
    }


    public void acceptOrder(Order order) throws InterruptedException {

            queue.put(order);


    }
    @Override
    public void run() {
        try {
            acceptOrder(order);
        } catch (InterruptedException e) {
            System.out.println("Order exception");
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
