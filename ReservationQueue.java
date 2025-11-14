import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReservationQueue {
     private static final BlockingQueue<Reservation> orderBlockingQueue = new LinkedBlockingQueue<>();

    public static BlockingQueue<Reservation> getOrderBlockingQueue() {
        return orderBlockingQueue;
    }
    public static boolean isQueueInitial(){
        return orderBlockingQueue.size() > 1;

    }

}
