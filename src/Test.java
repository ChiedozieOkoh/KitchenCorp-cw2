/*-------------------------------------------------------------------------------------------
 *   NAME: Test.java
 *   DATE: 20th April.java
 *   AUTHOR: Chiedozie Okoh
 *
 *   SUMMARY: Test harness for program functions.
 *            Tests stop early If an error is encountered.
 *
 *
 *-------------------------------------------------------------------------------------------*/

public class Test {
    public static void main(String args[]){

        SnackShop shop = new SnackShop("test");
        if (!testCustomerID()){
            System.err.println("Customer id tests failed! ");
            System.err.println("Exiting early...");
            System.exit(0);
        }

        if(!testCustomerBalance()){
            System.err.println("Customer balance tests failed! ");
            System.err.println("Exiting early...");
            System.exit(0);
        }

        if(!testProducts()){
            System.err.println("product tests failed! ");
            System.err.println("Exiting early...");
            System.exit(0);
        }

        if(!testTransaction()){
            System.err.println("transactions tests failed! ");
            System.err.println("Exiting early...");
            System.exit(0);
        }
        if(!testSort()){
            System.err.println("sorting tests failed! ");
            System.err.println("Exiting early...");
            System.exit(0);
        }

    }
    public static void printFailure(int testNum , String testSummary ,SimulationException e ){

        System.out.println( "[ERROR: Test " + testNum + " failed]");
        System.out.println("Test criteria: " + testSummary);
        if(e != null ){
            System.out.println("Reason for error: ");
            System.out.println(e.toString());
        }
        System.out.println(" ");
    }
    public static void printSuccess(int testNum, String testSummary, Object testObj ){
        System.out.println("[Test " + testNum + "] passed" + " with input: " + testObj.toString());
        System.out.println("Test criteria: " + testSummary);
        System.out.println(" ");
    }
    public static boolean testCustomerID(){
        String summary = "";
        /* test customers with valid id */
        /* we expect test 1 and 2 to succeed */

        summary = "assert that ID is alphanumeric";
        try{
            Customer ra = new Customer("VALID1","Rick Astley",999);
            printSuccess(1,summary,ra);

        }catch(InvalidCustomerException e ){
            printFailure(1,summary,e);
            return false;
        }
        summary = "assert that ID length 6 ";
        try{
            Customer jd = new Customer("KAPPAS","Josh DeSeno",100000);
            printSuccess(2,summary,jd);

            Customer jr = new Customer ("123456","Joe Rogan",100);
            printSuccess(2,summary,jr);

        }catch(InvalidCustomerException e ){
            printFailure(2,summary,e);
            return false;
        }


        /*
            test  customers with  invalid ID
            In this case we want the assertions to fail because that means our error checking is working correctly
            therefore we expect the tests 3 and 4 to fail
        */
        summary = "assert that ID length is 6";
        try {

            Customer ma = new Customer("INVALID", "Marcus Aurelius", 420);
            printSuccess(3,summary,ma);
            return false;
        }catch(InvalidCustomerException e ){
            printFailure(3,summary,e);
        }

        summary = "assert that ID is alphanumeric";
        try{
            Customer pa = new Customer("INVL!D","Plato Aristocles" , 100);
            printSuccess(4,summary,pa);
            return false;
        }catch(InvalidCustomerException e ){
            printFailure(4,summary,e);
        }
        System.out.println("CustomerID test ended with expected results ");
        System.out.println(" ");
        return true;
    }

    public static boolean testCustomerBalance(){
        String summary = "";

        summary = "assert that balance >= 0";
        try{
            Customer gf = new Customer("B0M3R7","Guy Fawkes",10);
            printSuccess(5,summary,gf);
        }catch(InvalidCustomerException e ){
            printFailure(5,summary,e);
            return false;
        }

        summary = "assert that balance is within student overdraft limit";
        try{
            StudentCustomer rs = new StudentCustomer("GNULNX","Richie Stallman",-1);
            printSuccess(6,summary,rs);

            StudentCustomer pj = new StudentCustomer("cv6900","Pheonix Jones" ,-500);
            printSuccess(6,summary,pj);
        }catch(InvalidCustomerException e ){
            printFailure(6,summary,e);
            return false;
        }

        try{
            StudentCustomer jd = new StudentCustomer("W3S5TD","Jimmy Donaldson",-1000000000);
            printSuccess(6,summary,jd);
            return false;
        }catch(InvalidCustomerException e ){
            printFailure(6,summary,e);
        }


        System.out.println("Customer balance test ended with expected results");
        System.out.println(" ");
        return true;
    }

    public static boolean testProducts(){
        String summary = "";

        /*test food id validation  */
        summary = "assert that food id starts with F-";
        try{
            Food banana = new Food("F-1234567","banana", Food.FOOD_TYPE.COLD,100);
            printSuccess(7,summary,banana);
        }catch(InvalidFoodException e ){
            printFailure(7,summary,e);
            return false;
        }

        try{
            Food apple = new Food("F 1234567","apple", Food.FOOD_TYPE.COLD,100);
            printSuccess(7,summary,apple);
            return false;
        }catch(InvalidProductException e ){
            printFailure(7,summary,e);
        }

        try{
            Food water = new Food("D-1234567","water", Food.FOOD_TYPE.COLD,100);
            printSuccess(7,summary,water);
            return false;
        }catch(InvalidFoodException e ){
            printFailure(7,summary,e);
        }

        summary = "assert that drink id starts with D-";
        try{
            Drink water = new Drink("D-1234567","water", Drink.SUGAR_LEVEL.NONE,100);
            printSuccess(8,summary,water);
        }catch(InvalidDrinkException e ){
            printFailure(8,summary,e);
            return false;
        }

        try{
            Drink apple = new Drink("D 1234567","apple", Drink.SUGAR_LEVEL.LOW,100);
            printSuccess(8,summary,apple);
            return false;
        }catch(InvalidProductException e ){
            printFailure(8,summary,e);
        }

        try{
            Drink orange = new Drink("F-1234567","orange", Drink.SUGAR_LEVEL.LOW,100);
            printSuccess(8,summary,orange);
            return false;
        }catch(InvalidDrinkException e ){
            printFailure(8,summary,e);
        }

        summary = "assert that product ids are 9 characters long";
        try{
            Drink fuji = new Drink("D-1234567","fuji", Drink.SUGAR_LEVEL.NONE,100);
        }catch(InvalidProductException e ){
            printFailure(9,summary,e);
            return false;
        }

        try{
            Food pizza = new Food("F-123456","pizza", Food.FOOD_TYPE.HOT,100);
            printSuccess(9,summary,pizza);
            return false;
        }catch(InvalidProductException e ){
            printFailure(9,summary,e);
        }

        summary = "assert that product tax is added correctly ";
        /*hot food gets 10% VAT */
        Food pizza = new Food("F-1234567","pizza", Food.FOOD_TYPE.HOT,100);
        if(pizza.calculatePrice() != 110){
            printFailure(10,summary,null);
            return false;
        }
        printSuccess(10,summary,pizza);

        /*check sugar tax is added correctly*/
        Drink water = new Drink("D-1234567","water", Drink.SUGAR_LEVEL.NONE,100);
        if(water.calculatePrice() != 100){
            printFailure(10,summary,null);
            return false;
        }
        printSuccess(10,summary,water);

        Drink orangeJuice = new Drink("D-1234567","orange juice", Drink.SUGAR_LEVEL.LOW,100);
        if(orangeJuice.calculatePrice() != 118){
            printFailure(10,summary,null);
            return false;
        }
        printSuccess(10,summary,orangeJuice);

        Drink oasis = new Drink("D-1234567","oasis", Drink.SUGAR_LEVEL.HIGH,100);
        if(oasis.calculatePrice() != 124){
            printFailure(10,summary,null);
            return false;
        }
        printSuccess(10,summary,oasis);

        System.out.println("Product test ended with expected results  ");
        System.out.println(" ");
        return true;
    }

    public static boolean testTransaction(){
        String summary = "assert that customers are charged correctly ";
        Customer jeff = new Customer("TEST12","jeff goodman",100);
        Food kitkat = new Food("F-6576421", "kitkat ", Food.FOOD_TYPE.COLD ,50);
        jeff.chargeAccount(kitkat.calculatePrice());
        if(jeff.getBalance() != 50 ){
            printFailure(11,summary,null);
            return false;
        }
        printSuccess(11,summary,jeff);

        Food iceCream = new Food("F-9786723","ice cream " , Food.FOOD_TYPE.COLD,60);
        try{
            jeff.chargeAccount(iceCream.calculatePrice());
            printFailure(11,summary,null);
            return false;
        }catch(InsufficientBalanceException e ){
            printSuccess(11,summary,jeff);
        }

        summary = "assert that students discount is accounted for correctly ";
        /*students have overdraft so they can be initialised with a negative balance within the overdraft allowance */
        final int dPrice = 100;
        StudentCustomer gs = new StudentCustomer("MADAx2","genji shimada" ,
                Math.round(dPrice-(dPrice*StudentCustomer.DISCOUNT)) -StudentCustomer.OVERDRAFT);

        Drink lemonade = new Drink("D-1234567","lemonade", Drink.SUGAR_LEVEL.NONE,dPrice);
        /*students get a 5% discount on all items so this and a 500 overdraft so this transaction should leave their balance at 0 */

        try{
            gs.chargeAccount(lemonade.calculatePrice());
            if(gs.getBalance() != 0){
                printFailure(12,summary,null);
                System.out.println("Item price was equal to the customer balance ");
                System.out.println("yet the account balance was not 0 once the transaction was complete");
                return false;
            }
            printSuccess(12,summary,gs);
        }catch(InsufficientBalanceException e ){
            printFailure(12,summary,e);
            return false;
        }



        summary = "assert that staff department discounted prices are calculated properly";
        try{
            StaffCustomer rp = new StaffCustomer("CO858D","Richard Pryor",
                    Math.round(dPrice - (dPrice*StaffCustomer.CMP_DISCOUNT)), StaffCustomer.DEPARTMENT.CMP);
            Drink coke = new Drink("D-1234567","coke", Drink.SUGAR_LEVEL.NONE,dPrice

            );
            System.out.println("item price: " + coke.calculatePrice());
            rp.chargeAccount(coke.calculatePrice());
            if(rp.getBalance() != 0){
                printFailure(13,summary,null);
                System.out.println("remaining balance: " + rp.getBalance());
                System.out.println("Item price was equal to the customer balance ");
                System.out.println("yet the account balance was not 0 once the transaction was complete");
                return false;
            }
            printSuccess(13,summary,rp);
        }catch(InsufficientBalanceException e){
            printFailure(13,summary,e);
            return false;
        }
        StaffCustomer hk = new StaffCustomer("MDL90X","Heidi Klum",
                Math.round(dPrice - (dPrice*StaffCustomer.CMP_DISCOUNT)) -1  , StaffCustomer.DEPARTMENT.CMP);
        try{
            Drink coke = new Drink("D-1234567","coke", Drink.SUGAR_LEVEL.NONE, dPrice);
            hk.chargeAccount(coke.calculatePrice());
            printFailure(13,summary,null);
            System.out.println(hk.toString());
            return false;
        }catch(InsufficientBalanceException e ){
            printSuccess(13,summary,hk);
        }

        System.out.println("Transaction test ended with expected results ");
        return true;
    }
    public static boolean testSort(){
        SnackShop shop = new SnackShop("test");
        Food f0 = new Food("F-0000000","zero", Food.FOOD_TYPE.COLD,1);
        Food f1 = new Food("F-1111111","one", Food.FOOD_TYPE.COLD,2);
        Food f2 = new Food("F-2222222","two", Food.FOOD_TYPE.COLD,3);
        Food f3 = new Food("F-3333333","three", Food.FOOD_TYPE.COLD,4);
        Food f4 = new Food("F-4444444","four", Food.FOOD_TYPE.COLD,5);
        shop.addProduct(f0);
        shop.addProduct(f1);
        shop.addProduct(f2);
        shop.addProduct(f3);
        shop.addProduct(f4);
        String summary = "assert that largest base price is calculated properly ";
        if (shop.findLargestBasePrice() != 5 ){
            printFailure(14,summary,null);
            return false;
        }
        printSuccess(14,summary,f4);


        Customer c0 = new Customer("000000","James Bond" , 1);
        Customer c1 = new Customer("111111","Don Pablo",2);
        Customer c2 = new Customer("222222","Elon Musk", 69);
        StudentCustomer c3 = new StudentCustomer("333333","Elliot Alderson",1);
        c3.chargeAccount(20);
        Customer c4 = new Customer("444444","Joe Pescas",7);
        shop.addCustomer(c0);
        shop.addCustomer(c1);
        shop.addCustomer(c2);
        shop.addCustomer(c3);
        shop.addCustomer(c4);
        summary = "assert that negative accounts are counted correctly";
        System.out.println("balance: " +c3.getBalance());
        System.out.println("negative accounts:" + shop.countNegativeAccounts() );
        if(shop.countNegativeAccounts() != 1){
            printFailure(15,summary,null);
            return false;
        }
        printSuccess(15,summary,c3);

        summary = "assert that median balance is calculated correctly";
        System.out.println(""+shop.calculateMedianBalance());
        if(shop.calculateMedianBalance() != c2.getBalance()){
            printFailure(16,summary,null);
            return false;
        }
        printSuccess(16,summary,c2);
        System.out.println("Sorting test ended with expected results");
        return true;
    }

}
