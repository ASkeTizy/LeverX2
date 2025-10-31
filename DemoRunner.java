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

    public List<Customer> createCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer().createOrder(new Order("Lenovo", 1)));
        customers.add(new Customer().createOrder(new Order("Macbook", 2)));
        customers.add(new Customer().createOrder(new Order("Philips monitor", 1)));
        customers.add(new Customer().createOrder(new Order("TeslaX", 3)));
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

    public void demoRun() {
        var customers = createCustomers();
        List<Future<Runnable>> threads = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (var customer : customers) {
            Future f = service.submit(customer);
            threads.add(f);
        }
        futureCaller(threads);
        threads.clear();
        var warehouse = new WareHouse();
        var workers = warehouse.getWorkers();
        for (var worker : workers) {
            Future f = service.submit(worker.takeOrderGetProduct());
            threads.add(f);
        }
        futureCaller(threads);
        service.shutdown();

        var val1 = warehouse.totalNumberOfOrdersCalculation();
        var val2 = warehouse.totalProfitCalculations();
        var val3 = warehouse.topThreeBestProductsCalculation();
        System.out.println(val1);
        System.out.println(val2);
        val3.forEach(System.out::println);
    }
}
