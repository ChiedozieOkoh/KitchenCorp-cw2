public class StudentCustomer extends Customer{
    private static final float DISCOUNT = 0.05f;
    private static final int OVERDRAFT = 500;
    public StudentCustomer(String id , String name , int balance ) {
        super(id,name,balance + OVERDRAFT);

    }

    public StudentCustomer(String id , String name ){
        super(id,name,OVERDRAFT);
        //addFunds(OVERDRAFT);
    }
    @Override
    public void chargeAccount(int charge) throws InsufficientBalanceException{
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
        super.chargeAccount(Math.round(fCharge));
    }
    @Override
    public String toString(){

        return getID() + delimiter + getName()+ delimiter + (getBalance() - OVERDRAFT) + delimiter + "STUDENT";
    }

}
