public class Customer  {
    private Reservation reservation;
    public ReservationProducer produceReservation(String name, Integer quantity) {
        var reservation = generateReservation(name,quantity);

        return new ReservationProducer(reservation);
    }
    private Reservation generateReservation(String name, Integer quantity) {
        return  new Reservation(name,quantity);
    }
    public ReservationProducer declineReservation(String name,Integer quantity) {
        var reservation = generateReservation(name, quantity);
        return new ReservationProducer(reservation);
    }
    public Reservation getReservation() {
        return reservation;
    }


}
