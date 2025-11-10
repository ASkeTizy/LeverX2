public class DeclineOperation extends CustomerOperation {


    public DeclineOperation(Reservation reservation) {
        super(reservation);
    }

    @Override
    public Integer proceed(Integer reservedQuantity, Integer storedQuantity) {
        if (reservedQuantity == null) {
            reservedQuantity = 0;
        }
        if (storedQuantity >= reservation.quantity() - reservedQuantity) {
            return reservation.quantity() - reservedQuantity;
        } else {
            throw new IllegalArgumentException("Cannot be more products than in stock");
        }
    }
}
