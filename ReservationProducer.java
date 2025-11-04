import java.util.concurrent.BlockingQueue;

public class ReservationProducer implements Runnable{
    BlockingQueue<Reservation> queue = ReservationQueue.getOrderBlockingQueue();
    Reservation reservation;
    public ReservationProducer( Reservation reservation) {

        this.reservation = reservation;
    }

    public BlockingQueue<Reservation> getQueue() {
        return queue;
    }



    @Override
    public void run() {

            try {
                queue.put(reservation);
            } catch (InterruptedException e) {
                System.out.println("Order exception");
                Thread.currentThread().interrupt();
            }

        }

}
