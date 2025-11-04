import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class WareHouse {
    private final ConcurrentMap<Product, Integer> hashMap = DemoRunner.generateWarehouseProducts();

    public ConcurrentMap<Product, Integer> getReservedProducts() {
        return reservedProducts;
    }

    private final ConcurrentMap<Product, Integer> reservedProducts = new ConcurrentHashMap<>();

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

    public List<Worker> getWorkers() {
        return workers;
    }


}
