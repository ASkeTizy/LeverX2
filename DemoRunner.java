import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DemoRunner {
    public void futureCaller(List<Future<Runnable>> threads) {
        for (Future<Runnable> f : threads) {
            try {
                f.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<OrderConsumer> generateOrders() {
        List<OrderConsumer> customers = new ArrayList<>();
        customers.add(new Customer().consumeOrder("Lenovo", 1));
        customers.add(new Customer().consumeOrder("Macbook", 2));
        customers.add(new Customer().consumeOrder("Philips monitor", 1));
        customers.add(new Customer().consumeOrder("TeslaX", 3));
        return customers;
    }

    public static ConcurrentMap<Product, Integer> generateWarehouseProducts() {

        List<Product> products = ProductCatalog.getProducts();
        return products.stream()
                .collect(Collectors.toConcurrentMap(product -> product,
                        product -> {
                            product.setQuantity(product.getQuantity() - 2);
                            return product.getQuantity();
                        }));
    }

    public static List<Product> generateProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Lenovo", 3, 2300.45));
        products.add(new Product("Macbook", 6, 3300.00));
        products.add(new Product("Philips monitor", 10, 300.35));
        products.add(new Product("TeslaX", 7, 6000.35));
        return products;
    }

    private void multiThreadExecutor(List<? extends Runnable> list, ExecutorService executor) {
        var futures = list.stream().map(el -> CompletableFuture.runAsync(el, executor))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
    }

    public void demoRun() {
        var orders = generateOrders();
        try (var executor = Executors.newFixedThreadPool(10)) {
            multiThreadExecutor(orders, executor);
            var warehouse = new WareHouse();
            var workers = warehouse.getWorkers().stream().map(worker->worker.produceOrder(warehouse)).toList();
            multiThreadExecutor(workers, executor);
            var totalNumberOfOrders = warehouse.totalNumberOfOrdersCalculation();
            var totalProfit = warehouse.totalProfitCalculations();
            var topThreeBestProducts = warehouse.topThreeBestProductsCalculation();
            System.out.println(totalNumberOfOrders);
            System.out.println(totalProfit);
            topThreeBestProducts.forEach(System.out::println);
        }
    }
}
