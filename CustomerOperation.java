public abstract class CustomerOperation {
    Reservation reservation;

    public CustomerOperation(Reservation reservation) {
        this.reservation = reservation;
    }
    public Reservation getReservation() {
        return reservation;
    }
    abstract Integer proceed(Integer reservedQuantity, Integer storedQuantity);
}
