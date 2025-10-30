import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class WareHouse {
    private ConcurrentMap<Product, Integer> hashMap = new ConcurrentHashMap<>();
    private List<Order> orders = new ArrayList<>();
    private final List<Worker> workers = new ArrayList<>();

    public WareHouse() {
        generateWorkers();
        generateHasMap();
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

    private void generateHasMap() {
        List<Product> products = ProductCatalog.getProducts();
        hashMap = products.stream()
                .collect(Collectors.toConcurrentMap(product -> product,
                        product -> {
                            product.setQuantity(product.getQuantity() - 2);
                            return product.getQuantity();
                        }));
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public long totalNumberOfOrdersCalculation() {
        return orders.parallelStream().count();
    }
    private List<Product> executedOrders() {
        var products = ProductCatalog.getProducts();
        return products.parallelStream()
                .peek(product -> {
                    List<Order> order = orders.stream().filter(el->el.productName().equals(product.getName())).toList();
                    product.setQuantity(order.stream().mapToInt(Order::productQuantity).reduce(0,Integer::sum));
                }).toList();
    }
    public Double totalProfitCalculations() {

        return executedOrders().parallelStream()
                .mapToDouble(product -> product.getQuantity() * product.getPrice())
                .reduce(0, Double::sum);
    }

    public List<Product> topThreeBestProductsCalculation() {

        return executedOrders().parallelStream().sorted(Comparator.comparingInt(Product::getQuantity).reversed()).limit(3).toList();
    }
}
