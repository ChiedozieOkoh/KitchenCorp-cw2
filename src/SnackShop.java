import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SnackShop {
    private static final int FALSE = 0;
    private static final int TRUE = 1;


    private final String name ;
    private HashMap<String , Customer>customerMap = new HashMap<>();
    private HashMap<String,Product>productMap = new HashMap<>();

    public SnackShop(String name ){
        this.name = name;
    }

    public void addCustomer(Customer customer ){
        customerMap.put(customer.getID(), customer);
    }

    public void addProduct(Product product ){
        productMap.put(product.getID(),product);
    }
    public String getCustomer (String id){
        Customer customer = customerMap.get(id);
        if(customer == null){
            InvalidCustomerException e = new InvalidCustomerException();
            e.setMessage("Customer: " + id + " could not be found");
            throw e;
        }
        return customer.toString();
    }
    public String getProduct(String id ){
        Product product = productMap.get(id);
        if(product == null){
            InvalidProductException e  = new InvalidProductException();
            e.setMessage("Product: " + id + " could not be found");
            throw e;
        }
        return product.toString();
    }

    public boolean processTransaction(String customerID , String productID) throws SimulationException{
        Customer customer = customerMap.get(customerID);
        if (customer == null ){
            InvalidCustomerException e = new InvalidCustomerException();
            e.setMessage("Customer: " + customerID + " could not be found");
            throw e;
        }
        Product product = productMap.get(productID);
        if(product == null){
            InvalidProductException e  = new InvalidProductException();
            e.setMessage("Product: " + productID + " could not be found");
            throw e;
        }

        try {
            customer.chargeAccount(product.calculatePrice());
        }catch(InsufficientBalanceException e ){
            throw e ;
        }

        return true;
    }


    /*returns a sorted list of all values in a given map */
    private <T>ArrayList<T> sortMap(HashMap<String,T>map , Comparator<T>comparator){
        ArrayList<T>Values = new ArrayList<T>(
                map.values()
                        .stream()
                        .toList()
        );
        Values.sort(comparator);
        return Values;
    }

    private static class CompareBalance implements Comparator<Customer>{

        @Override
        public int compare (Customer c1, Customer c2){
            return Integer.compare(c1.getBalance(),c2.getBalance());
        }
    }

    private static class CompareBasePrice implements Comparator<Product>{
        @Override
        public int compare(Product p1, Product p2){
            return Integer.compare(p1.getBasePrice(),p2.getBasePrice());
        }
    }

    public int findLargestBasePrice(){
        ArrayList<Product>shortList = sortMap(productMap,new CompareBasePrice());
        /* ascending  numeric order is the default for java */
        int end = shortList.size() - 1;
        return shortList.get(end).getBasePrice();
    }

    public int countNegativeAccounts() {
        int count=0;
        ArrayList<Customer> customers = new ArrayList<>(customerMap.values()
            .stream().collect(Collectors.toUnmodifiableList()));
        for(Customer i : customers){
            if (i.getBalance() < 0 ){
                count++;
            }
        }
        return count;
    }
    public float calculateMedianBalance(){
        ArrayList<Customer> customers = sortMap(customerMap, new CompareBalance());
        int midPoint = Math.round((float)customers.size() / 2.0f);
        if (customers.size() % 2 == 0){
            /* there is no middle, take the average  */
            return (float)customers.get(midPoint).getBalance() + (float)customers.get(midPoint-1).getBalance() / 2.0f;
        }
        return customers.get(midPoint).getBalance();
    }
}
