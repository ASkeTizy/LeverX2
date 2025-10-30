import java.util.concurrent.BlockingQueue;

public class Customer implements Runnable {
    private Order order;
    public Customer createOrder(Order order) {
        this.order = order;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void run() {
        BlockingQueue<Order> queue = OrderQueue.getOrderBlockingQueue();
        try {
            queue.put(getOrder());
        } catch (InterruptedException e) {
            System.out.println("Order exception");
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }


}
