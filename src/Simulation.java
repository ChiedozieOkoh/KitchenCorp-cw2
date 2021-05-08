/*-------------------------------------------------------------------------------------------
 *   NAME: Simulation.java
 *   DATE: 20th April.java
 *   AUTHOR: Chiedozie Okoh
 *
 *   NOTES:
 *       Initialises SnackShop with necessary files
 *
 *
 *-------------------------------------------------------------------------------------------*/
import java.io.File;
import java.io.FileNotFoundException;

public class Simulation {
    public static void main (String args [] ){

        try{
            File productFile = new File("products.txt");
            File customerFile = new File("customers.txt");
            File ledger = new File("transactions.txt");
            SnackShop snackShop = initialiseShop("snaq",productFile,customerFile);
            simulateShopping(snackShop,ledger);
        }catch(FileNotFoundException e ){
            System.err.println("Could not find simulation files");
        }
    }
    public static SnackShop initialiseShop (String shopName , File productFile , File customerFile )throws FileNotFoundException{
        SnackShop shop = new SnackShop(shopName);
        shop.parseCustomerFile(customerFile);
        shop.parseProductFile(productFile);
        return shop;


    }
    public static void simulateShopping (SnackShop shop , File transactionFile )throws FileNotFoundException{

        shop.parseTransactions(transactionFile);
        System.out.println("Turnover: " + shop.getTurnOver());
    }

}
