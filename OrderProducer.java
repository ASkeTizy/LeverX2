import java.util.List;
import java.util.concurrent.BlockingQueue;

public class OrderProducer implements Runnable{
    BlockingQueue<Order> queue = OrderQueue.getOrderBlockingQueue();
    Order order;
    public OrderProducer( Order order) {

        this.order = order;
    }

    public BlockingQueue<Order> getQueue() {
        return queue;
    }



    @Override
    public void run() {

            try {
                queue.put(order);
            } catch (InterruptedException e) {
                System.out.println("Order exception");
                Thread.currentThread().interrupt();
            }

        }

}
