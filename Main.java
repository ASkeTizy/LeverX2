
public void threadCaller(List<Future<Runnable>> threads) {
    for (Future<Runnable> f : threads)
    {
        try {
            f.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

void main() {
    List<Customer> customers = new ArrayList<>();
    List<Future<Runnable>> threads = new ArrayList<>();
    ExecutorService service = Executors.newFixedThreadPool(10);
    customers.add(new Customer().createOrder(new Order("Lenovo", 1)));
    customers.add(new Customer().createOrder(new Order("Macbook", 2)));
    customers.add(new Customer().createOrder(new Order("Philips monitor", 1)));
    customers.add(new Customer().createOrder(new Order("TeslaX", 3)));
//    new Customer().createOrder(new Order("Lenovo", 1));
    for (var customer : customers) {
        Future f = service.submit(customer);
        threads.add(f);
    }
    threadCaller(threads);
    threads.clear();
    var warehouse = new WareHouse();
    var workers = warehouse.getWorkers();
    for(var worker:workers){
        Future f = service.submit(worker.takeOrderGetProduct());
        threads.add(f);
    }
    threadCaller(threads);
    service.shutdown();

    var val1 = warehouse.totalNumberOfOrdersCalculation();
    var val2 = warehouse.totalProfitCalculations();
    var val3 = warehouse.topThreeBestProductsCalculation();
    System.out.println(val1);
    System.out.println(val2);
    val3.forEach(System.out::println);
}


