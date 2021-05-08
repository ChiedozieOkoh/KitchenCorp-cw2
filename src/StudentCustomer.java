/*-------------------------------------------------------------------------------------------
 *   NAME: StudentCustomer.java
 *   DATE: 20th April.java
 *   AUTHOR: Chiedozie Okoh
 *
 *   SUMMARY: handles calculation of students discounts for transactions.
 *            Keeps track of student overdrafts.
 *            Also controls serialisation of staff objects.
 *
 *   NOTES:
 *          *************************************************
 *          Use isNegative() over getBalance() where possible
 *          *************************************************
 *          The balance of a studentCustomer is added
 *          with the absolute value of student overdraft. this is because negative balances
 *          will be rejected by the base class' constructor.
 *          The spec says this class should have two constructors which match the ones in the Customer class.
 *          Therefore I will not override this functionality of the base class
 *          as that would involve adding an empty default constructor to the StudentCustomer.
 *
 *          This means that if you initalise a customer and a studentCustomer
 *          with the same balance and compare the balances using getBalance()
 *          the studentCustomer will always appear to have more money. This is not a bug its a feature
 *
 *
 *
 *
 *-------------------------------------------------------------------------------------------*/
public class StudentCustomer extends Customer{
    public static final float DISCOUNT = 0.05f;
    public static final int OVERDRAFT = 500;
    public StudentCustomer(String id , String name , int balance )throws InvalidCustomerException{
        super(id,name,balance + OVERDRAFT);

    }

    public StudentCustomer(String id , String name )throws InvalidCustomerException{
        super(id,name,OVERDRAFT);
        //addFunds(OVERDRAFT);
    }
    @Override
    public int chargeAccount(int charge) throws InsufficientBalanceException{
        float fCharge = charge;
        fCharge = fCharge - fCharge*DISCOUNT;
        int result = (getBalance()) - Math.round(fCharge);
        if (result < 0 ){
            InsufficientBalanceException e = new InsufficientBalanceException();
            e.setMessage("customer:" + System.lineSeparator() +
                    toString() + " has insufficient funds" + System.lineSeparator() +
                    "item charge was:" + fCharge );
            throw e;
        }
        return super.chargeAccount(Math.round(fCharge));
    }
    @Override
    public boolean isNegative(){
        return getBalance() < OVERDRAFT;
    }
    @Override
    public String toString(){
        /*OVERDRAFT is subtracted from balance so the objects balance is in range with other customer objects*/
        return getID() + delimiter + getName()+ delimiter + (getBalance() - OVERDRAFT ) + delimiter + "STUDENT";
    }




}
