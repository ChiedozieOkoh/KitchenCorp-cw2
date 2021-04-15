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
            e.printStackTrace();
        }
    }
    public static SnackShop initialiseShop (String shopName , File productFile , File customerFile )throws FileNotFoundException{
        SnackShop shop = new SnackShop(shopName);
        shop.parseCustomerFile(customerFile);
        shop.parseProductFile(productFile);
        return shop;


    }
    public static void simulateShopping (SnackShop shop , File ledger )throws FileNotFoundException{

        shop.parseTransactions(ledger);
        System.out.println("Turnover: " + shop.getTurnOver());
    }

}
