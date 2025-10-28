import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WareHouse {
    private ConcurrentHashMap<Product,Integer> hashMap = new ConcurrentHashMap<>();
    private final List<Worker> workers = new ArrayList<>();

    public WareHouse() {
        generateWorkers();
    }

    public ConcurrentHashMap<Product, Integer> getHashMap() {
        return hashMap;
    }

    private void generateWorkers(){
        workers.add(new Worker(this));
        workers.add(new Worker(this));
        workers.add(new Worker(this));
        workers.add(new Worker(this));
    }

    public List<Worker> getWorkers() {
        return workers;
    }
}
