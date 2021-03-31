public class StaffCustomer extends Customer{
    public enum DEPARTMENT {
        BIO,
        CMP,
        MTH,
        OTHER,
        NONE
    }
    private final DEPARTMENT M_DEPARTMENT;
    public StaffCustomer(String id , String name , int balance , DEPARTMENT department ){
        super(id,name,balance);
        M_DEPARTMENT = department;
    }
    public StaffCustomer(String id , String name , DEPARTMENT department){
        super(id,name,0);
        M_DEPARTMENT = department ;
    }

    @Override
    public void chargeAccount(int charge ){
        float discount = 0.0f;
        float fCharge = charge;

        switch (M_DEPARTMENT){
            case BIO:
                discount = 0.025f;
                break;
            case CMP:
                discount = 0.1f;
                break;
            case MTH:
                discount = 0.025f;
        }
        fCharge = fCharge - (fCharge*discount);
         super.chargeAccount(Math.round(fCharge));

    }
    @Override
    public String toString(){
        String department = "";
        switch (M_DEPARTMENT){
            case BIO:
                department = "BIO";
                break;
            case CMP:
                department = "CMP";
                break;
            case MTH:
                department = "MTH";
        }
        return super.toString() + delimiter + "STAFF" + delimiter + department;
    }

}
