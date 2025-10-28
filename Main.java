//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

        new Customer().createOrder(new Order("Laptop",1));
        new Customer().createOrder(new Order("Macbook",2));
        new Customer().createOrder(new Order("Philips Monitor",1));
        new Customer().createOrder(new Order("Laptop",1));
        var workers = new WareHouse().getWorkers();
        workers.forEach(Worker::takeOrderGetProduct);
        ProductCatalog.getProducts().forEach(System.out::println);

}
