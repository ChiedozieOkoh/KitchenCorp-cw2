import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SnackShop {

    private final int CUSTOMER_ID_INDEX = 0;
    private final int CUSTOMER_NAME_INDEX = 1;
    private final int CUSTOMER_BALANCE_INDEX = 2;
    private final int CUSTOMER_TYPE_ENUM_INDEX = 3;
    private final int CUSTOMER_DEPARTMENT_INDEX = 4;

    private  final int PRODUCT_ID_INDEX = 0;
    private final int PRODUCT_NAME_INDEX = 1;
    private final int PRODUCT_TYPE_ENUM_INDEX = 2;
    private final int PRODUCT_PRICE_INDEX = 3;


    private final String name ;
    private HashMap<String , Customer>customerMap = new HashMap<>();
    private HashMap<String,Product>productMap = new HashMap<>();
    private int turnOver = 0;



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

    private boolean processTransaction(String customerID , String productID) throws SimulationException{
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
             int profit = customer.chargeAccount(product.calculatePrice());
             turnOver += profit;
        }catch(InsufficientBalanceException e ){
            throw e ;
        }

        return true;
    }


    /*returns a sorted list of all values in a given map */
    private <T>ArrayList<T> sortMap(HashMap<String,T>map , Comparator<T>comparator){
        ArrayList<T>Values = new ArrayList<T>(
                map.values()

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

    public boolean parseCustomerFile(File customerFile)throws FileNotFoundException{
        Scanner reader = new Scanner(customerFile);

        while(reader.hasNextLine()){
            String line = reader.nextLine();
            String  fields[] = line.split(String.valueOf(Customer.delimiter));

            if(fields.length == 3 ){
                try {
                    parseGenericCustomer(fields);
                    System.out.println(" Customer : " + line + " parsed successfully");
                }catch(InvalidCustomerException e ){
                    System.err.println("Unrecognised customer: ");
                    System.err.println(line);
                    System.err.println(e.toString());
                    System.err.println(" ");
                }
            }else{
            switch(fields[CUSTOMER_TYPE_ENUM_INDEX]){
                case "STAFF":
                    try {
                        parseStaffCustomer(fields);
                        System.out.println(" Customer : " + line + " parsed successfully");
                    }catch(InvalidCustomerException e ){
                        System.err.println("Unrecognised staff customer: ");
                        System.err.println(line);
                        System.err.println(e.toString());
                        System.err.println(" ");
                    }
                    break;
                case "STUDENT":
                    try {
                        parseStudentCustomer(fields);
                        System.out.println(" Customer : " + line + " parsed successfully");
                    }catch(InvalidCustomerException e ){
                        System.err.println("Unrecognised student customer: ");
                        System.err.println(line);
                        System.err.println(e.toString());
                        System.err.println(" ");
                    }
            }
        }

        }

        return true;
    }
    public boolean parseProductFile(File productFile)throws FileNotFoundException{
        Scanner reader = new Scanner(productFile);
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            String fields[] = line.split(String.valueOf(Product.delimiter));
            boolean isValid = false;
            if(fields[PRODUCT_TYPE_ENUM_INDEX].equals("hot") | fields[PRODUCT_TYPE_ENUM_INDEX].equals("cold")){
                try{
                    isValid = parseFood(fields);
                    System.out.println("product: " + line + " parsed successfully ");
                }catch(InvalidFoodException e){
                    System.err.println("Unrecognised food item: ");
                    System.err.println(line);
                    System.err.println(e.toString());
                    System.err.println(" ");
                }
            }

            if(fields[PRODUCT_TYPE_ENUM_INDEX].equals("none")
                |fields[PRODUCT_TYPE_ENUM_INDEX].equals("low")
                |fields[PRODUCT_TYPE_ENUM_INDEX].equals("high")
            ){
                try{
                    isValid = parseDrink(fields);
                    System.out.println("product: " + line + " parsed successfully ");
                }catch(InvalidDrinkException e ){
                    System.err.println("Error: Unrecognised drink item: ");
                    System.err.println(line);
                    System.err.println(e.toString());
                    System.err.println(" ");
                }
            }
            if(!isValid){
                System.err.println("Error: Unrecognised product: ");
                System.err.println(line);
                System.err.println(" ");
            }

        }
        return true;
    }
    private boolean parseGenericCustomer(String fields[])throws InvalidCustomerException{
            try {
                Customer c = new Customer(
                        fields[CUSTOMER_ID_INDEX],
                        fields[CUSTOMER_NAME_INDEX],
                        Integer.parseInt(fields[CUSTOMER_BALANCE_INDEX])
                );
                addCustomer(c);

            }catch(InvalidCustomerException e ){
                throw e ;
            }

        return true;
    }

    private boolean parseStaffCustomer(String fields[])throws InvalidCustomerException{
            if(fields.length == 4 ){
                StaffCustomer sCustomer = new StaffCustomer(
                    fields[CUSTOMER_ID_INDEX],
                    fields[CUSTOMER_NAME_INDEX],
                    Integer.parseInt(fields[CUSTOMER_BALANCE_INDEX]),
                    StaffCustomer.DEPARTMENT.NONE

                );
                addCustomer(sCustomer);
            }else {
                StaffCustomer.DEPARTMENT department = StaffCustomer.DEPARTMENT.NONE;
                switch (fields[CUSTOMER_DEPARTMENT_INDEX]) {
                    case "BIO":
                        department = StaffCustomer.DEPARTMENT.BIO;
                        break;
                    case "CMP":
                        department = StaffCustomer.DEPARTMENT.CMP;
                        break;
                    case "MTH":
                        department = StaffCustomer.DEPARTMENT.MTH;
                }
                StaffCustomer sCustomer = new StaffCustomer(
                        fields[CUSTOMER_ID_INDEX],
                        fields[CUSTOMER_NAME_INDEX],
                        Integer.parseInt(fields[CUSTOMER_BALANCE_INDEX]),
                        department
                );
                addCustomer(sCustomer);
            }
            return true;
    }

    private boolean parseStudentCustomer(String fields[]){
        StudentCustomer sCustomer = new StudentCustomer(
            fields[CUSTOMER_ID_INDEX],
            fields[CUSTOMER_NAME_INDEX],
            Integer.parseInt(fields[CUSTOMER_BALANCE_INDEX])
        );
        addCustomer(sCustomer);
        return true;
    }


    /*used to read food items in the product file  */
    private boolean parseFood(String fields[])throws InvalidFoodException{
        Food.FOOD_TYPE fType = Food.FOOD_TYPE.COLD;
        if (fields[PRODUCT_TYPE_ENUM_INDEX].equals("hot")){
            fType = Food.FOOD_TYPE.HOT;
        }

        Food food = new Food(
            fields[PRODUCT_ID_INDEX],
            fields[PRODUCT_NAME_INDEX],
            fType,
            Integer.parseInt(fields[PRODUCT_PRICE_INDEX])
        );
        addProduct(food);

        return true;
    }
    /*used to read drink items in the product file */
    private boolean parseDrink(String fields []) throws InvalidDrinkException {
        Drink.SUGAR_LEVEL sLevel = Drink.SUGAR_LEVEL.NONE;
        switch (fields[PRODUCT_TYPE_ENUM_INDEX]) {
            case "low":
                sLevel = Drink.SUGAR_LEVEL.LOW;
                break;
            case "high":
                sLevel = Drink.SUGAR_LEVEL.HIGH;
        }

        Drink drink = new Drink(
                fields[PRODUCT_ID_INDEX],
                fields[PRODUCT_NAME_INDEX],
                sLevel,
                Integer.parseInt(fields[PRODUCT_PRICE_INDEX])
        );
        addProduct(drink);
        return true;
    }

    /*handles the transaction file  */
    public boolean parseTransactions(File ledger )throws FileNotFoundException {
        final int ACTION_INDEX = 0;
        final int TYPE_ENUM_INDEX = 4;
        Scanner reader = new Scanner(ledger);
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            String fields[] = line.split(",");
            switch(fields[ACTION_INDEX]){
                case "NEW_CUSTOMER":
                    if(!handleNewCustomer(fields)){
                        System.err.println("unrecognised transaction: ");
                        System.err.println(line);
                        System.err.println("transaction will be ignored");
                        System.err.println(" ");
                    }
                    System.out.println("transaction: " + line + " was successful");
                    break;
                case "ADD_FUNDS":
                    if (!handleAddFunds(fields)){
                        System.err.println("unrecognised transaction: ");
                        System.err.println(line);
                        System.err.println("transaction will be ignored");
                        System.err.println(" ");
                    }
                    System.out.println("transaction: " + line + " was successful");
                    break;
                case "PURCHASE":
                    try{
                        handlePurchase(fields);
                        System.out.println("transaction: " + line + " was successful");
                    }catch(SimulationException e ){
                        System.err.println("error in transaction: ");
                        System.err.println(line);
                        System.err.println(e.toString());
                        System.err.println(" ");
                    }
            }

        }

        return true;
    }
    /*helper function to processing customers in the transaction file  */
    private boolean handleNewCustomer(String fields[]){
        if(fields.length >=4) {
            String test = fields[3];
            if (!isNumeric(test)) {
                /*assume it is either a new student or staff customer */
                switch(test){
                    case "STAFF":
                        return parseNewStaffCustomer(fields);
                        //break;
                    case "STUDENT":
                        return parseNewStudent(fields);
                        //break;
                    default:
                       return false;
                }
            }
            return parseNewGenericCustomer(fields);
        }else{
            return false;
        }
    }
    /* handles new generic customer in the transaction file  */
    private boolean parseNewGenericCustomer(String fields[]){
        final int ID_INDEX = 1;
        final int NAME_INDEX = 2;
        final int BALANCE_INDEX = 3;
        if (fields.length == 3){
            parseGenericCustomer(
                    new String[]{
                            fields[ID_INDEX],
                            fields[NAME_INDEX],
                            "0"
                    }
            );
            return true;
        }

        if (fields.length == 4 ){
            parseGenericCustomer(
                    new String[]{
                            fields[ID_INDEX],
                            fields[NAME_INDEX],
                            fields[BALANCE_INDEX]
                    }
            );
            return true;
        }
        return false;
    }
    /* handles new student customers  in the transaction file */
    private boolean parseNewStudent(String fields[] ){
        final int ID_INDEX = 1;
        final int NAME_INDEX = 2;
        final int BALANCE_INDEX = 4;
        return parseStudentCustomer(
                new String[]{
                        fields[ID_INDEX],
                        fields[NAME_INDEX],
                        fields[BALANCE_INDEX]
                }
        );

    }

    /* handles new staff customers in the transaction file */
    private boolean parseNewStaffCustomer(String fields[]){
        final int ID_INDEX = 1;
        final int NAME_INDEX = 2;
        final int TYPE_ENUM_INDEX = 3;

        if(fields.length == 5 ){
            /*then department was omitted*/
            final int BALANCE_INDEX = 4;
            parseStaffCustomer(
                    new String[]{
                            fields[ID_INDEX],
                            fields[NAME_INDEX],
                            fields[BALANCE_INDEX],
                            "none"
                    }
            );
            return true;
        }

        if(fields.length == 6 ){
            final int DEPARTMENT_INDEX = 4;
            final int BALANCE_INDEX =5;

            parseStaffCustomer(
                    new String[]{
                            fields[ID_INDEX],
                            fields[NAME_INDEX],
                            fields[BALANCE_INDEX],
                            fields[DEPARTMENT_INDEX]
                    }
            );
            return true;
        }

        return false;
    }


    private boolean isNumeric(String str ){
        for (int i = 0;  i < str.length() ; i++ ){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    /*handles the add_funds transaction in the transaction file */
    private boolean handleAddFunds(String fields[]) {
        final int ID_INDEX = 1;
        final int FUNDS_INDEX = 2;
        Customer customer = customerMap.get(fields[ID_INDEX]);
        if (customer == null){
            return false;
        }
        if(!isNumeric(fields[FUNDS_INDEX])){
            return  false;
        }
        customer.addFunds(Integer.parseInt(fields[FUNDS_INDEX]));
        return true;
    }
    /*validates a purchase in the transaction file  */
    private boolean handlePurchase(String fields[])throws SimulationException{
        final int CUSTOMER_ID = 1;
        final int PRODUCT_ID = 2;
        return processTransaction(fields[CUSTOMER_ID],fields[PRODUCT_ID]);
    }
    public int getTurnOver(){
        return turnOver;
    }
}
