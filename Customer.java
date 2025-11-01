import java.util.concurrent.BlockingQueue;

public class Customer  {
    private Order order;
    public OrderConsumer consumeOrder(String name,Integer quantity) {
        var order = new Order(name,quantity);

        return new OrderConsumer(order);
    }

    public Order getOrder() {
        return order;
    }


}
