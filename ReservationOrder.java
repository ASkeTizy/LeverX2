import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public  class ReservationOrder {

    private  static final List<Reservation> reservations = new CopyOnWriteArrayList<>();



    public static List<Reservation> getReservations() {
        return reservations;
    }

}
