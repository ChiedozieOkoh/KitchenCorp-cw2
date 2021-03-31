public class Simulation {
    public static void main (String args [] ){
        StaffCustomer gavin = new StaffCustomer("42069K","gavin", StaffCustomer.DEPARTMENT.CMP);
        gavin.chargeAccount(0);
        StudentCustomer jeff = new StudentCustomer("EZCLAP","jeff",-1);
        System.out.println(jeff.getBalance());
        jeff.chargeAccount(526);
        System.out.println(jeff.getBalance());
        System.out.println(jeff.toString());
        System.out.println(StaffCustomer.delimiter);
    }

}
