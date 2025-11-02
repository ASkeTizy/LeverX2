public class Customer  {
    private Order order;
    public OrderProducer produceOrder(String name, Integer quantity) {
        var order = new Order(name,quantity);

        return new OrderProducer(order);
    }

    public Order getOrder() {
        return order;
    }


}
