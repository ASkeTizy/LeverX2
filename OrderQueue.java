import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderQueue {
     private static final BlockingQueue<Order> orderBlockingQueue = new LinkedBlockingQueue<>();

    public static BlockingQueue<Order> getOrderBlockingQueue() {
        return orderBlockingQueue;
    }
}
