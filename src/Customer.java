public class Customer {
    public static final char delimiter = '#';
    private final String id ;
    private final String name;
    private int balance  = 0;
    public Customer(String id, String name , int balance )throws InvalidCustomerException{
        validate(id,balance);
        this.id = id ;
        this.name = name;
        this.balance = balance;
    }
    public Customer (String id ,String name )throws InvalidCustomerException{
        validate(id,balance);
        this.id = id ;
        this.name = name;

    }

    private void validate(String id ,int balance) throws InvalidCustomerException{
        final int requiredLength = 6;
        if(id.length() != 6 ){
            InvalidCustomerException e = new InvalidCustomerException();
            e.setMessage("invalid customer ID: " + id + " " + System.lineSeparator() +
                         "ID must be 6 characters long ");
            throw e ;
        }
        if(balance < 0 ){
            InvalidCustomerException e = new InvalidCustomerException();
            e.setMessage("invalid customer account: " + id + " " + System.lineSeparator() +
                         "balance cannot be initialised as negative");
            throw e;
        }
        for(int i = 0 ; i < requiredLength; i++){
            if(!Character.isAlphabetic(id.charAt(i)) && !Character.isDigit(id.charAt(i))){
                InvalidCustomerException e = new InvalidCustomerException();
                e.setMessage("invalid customer ID: " + id + System.lineSeparator() +
                             "id may only be alphanumeric ");
                throw e;
            }
        }
    }
    @Override
    public String toString(){
        return id + delimiter + name + delimiter + balance ;
    }
    public void addFunds(int amount ){
        balance += amount;
    }
    public void chargeAccount(int charge) throws InsufficientBalanceException{
        if ((balance - charge) < 0 ){
            InsufficientBalanceException e = new InsufficientBalanceException();
            e.setMessage("customer:" + System.lineSeparator() +
                    toString() + " has insufficient funds" + System.lineSeparator() +
                    "item charge was: " + charge + System.lineSeparator() +
                    "transaction declined");
            throw e;
        }

        balance = balance - charge;
    }
    public int getBalance(){
        return balance;
    }
    public String getID(){
        return id ;
    }
    public String getName(){
        return name;
    }


}
