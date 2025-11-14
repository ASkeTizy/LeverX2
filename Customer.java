public class Customer  {

    private Reservation reservation;
    public OrderProducer produceOrder(String name, Integer quantity) {
        var order = new Order(name,quantity);

        return new OrderProducer(order);
    }


    public void reserveProduct(String name,Integer quantity) {
        reservation = new Reservation(name,quantity);
        ReservationOrder.getReservations().add(reservation);
    }
    public void declineProduct() {
        ReservationOrder.getReservations().remove(reservation);
    }

}
