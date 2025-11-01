import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

public class Worker {
    WareHouse wareHouse;
    public WorkerProducer produceOrder(WareHouse wareHouse) {

        return new WorkerProducer(wareHouse);
    }

    public Worker(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }




}
