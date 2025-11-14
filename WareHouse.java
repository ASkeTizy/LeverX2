import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class WareHouse {
    private final ConcurrentMap<Product, Integer> hashMap = DemoRunner.generateWarehouseProducts();

    private List<Order> orders = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    private final List<Worker> workers = new ArrayList<>();

    public WareHouse() {
        generateWorkers();

    }

    public ConcurrentMap<Product, Integer> getHashMap() {
        return hashMap;
    }

    private void generateWorkers() {
        workers.add(new Worker(this));
        workers.add(new Worker(this));
        workers.add(new Worker(this));
        workers.add(new Worker(this));
    }



    public List<Order> getOrders() {
        return orders;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    private boolean compareOrderProductByName(Product product, Order order) {
        return product.getName().equals(order.productName());
    }
    private Integer calculateOrderedSum(Product product) {
        return orders.stream()
                .filter(order -> compareOrderProductByName(product, order))
                .mapToInt(Order::productQuantity)
                .reduce(0, Integer::sum);
    }
    public long totalNumberOfOrdersCalculation() {
        return orders.parallelStream().count();
    }

    private List<Product> executedOrders() {
        var products = ProductCatalog.getProducts();
        return products.parallelStream()
                .peek(product -> {
                    Integer orderedQuantity = calculateOrderedSum(product);
                    product.setQuantity(orderedQuantity);
                }).toList();
    }

    public Double totalProfitCalculations() {

        return executedOrders().parallelStream()
                .mapToDouble(product -> product.getQuantity() * product.getPrice())
                .reduce(0, Double::sum);
    }

    public List<Product> topThreeBestProductsCalculation() {

        return executedOrders().parallelStream()
                .sorted(Comparator.comparingInt(Product::getQuantity).reversed())
                .limit(3)
                .toList();
    }
}
