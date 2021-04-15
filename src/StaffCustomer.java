public class StaffCustomer extends Customer{
    public   enum DEPARTMENT {
        BIO,
        CMP,
        MTH,
        OTHER,
        NONE
    }
    private final DEPARTMENT M_DEPARTMENT;
    public static final float BIO_DISCOUNT = 0.025f;
    public static final float CMP_DISCOUNT = 0.1f;
    public static final float MTH_DISCOUNT = BIO_DISCOUNT;
    public StaffCustomer(String id , String name , int balance , DEPARTMENT department ){
        super(id,name,balance);
        M_DEPARTMENT = department;
    }
    public StaffCustomer(String id , String name , DEPARTMENT department){
        super(id,name,0);
        M_DEPARTMENT = department ;
    }

    @Override
    public int chargeAccount(int charge ){
        float discount = 0.0f;
        float fCharge = charge;

        switch (M_DEPARTMENT){
            case BIO:
                discount = BIO_DISCOUNT;
                break;
            case CMP:
                discount = CMP_DISCOUNT;
                break;
            case MTH:
                discount = MTH_DISCOUNT;
        }
        fCharge = fCharge - (fCharge*discount);
        System.out.println("staff charged " + Math.round(fCharge));
         return super.chargeAccount(Math.round(fCharge));

    }
    @Override
    public String toString() {
        String department = "";
        switch (M_DEPARTMENT) {
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
