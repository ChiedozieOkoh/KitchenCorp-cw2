/*-------------------------------------------------------------------------------------------
*   NAME: Drink.java
*   DATE: 20th April.java
*   AUTHOR: Chiedozie Okoh
*
*   SUMMARY: Implentation of Product class. Encapsulates calculating the price of a Drink
*            and serialising  drink objects
*
*   NOTES:
*       inherits from Product
*
*   PUBLIC FIELDS:
*       ENUMERATE: SUGAR_LEVEL
*-------------------------------------------------------------------------------------------*/
public class Drink extends Product{
    public enum SUGAR_LEVEL{
        HIGH ,
        LOW ,
        NONE
    }
    private final SUGAR_LEVEL S_LEVEL;
    public Drink(String id , String name , SUGAR_LEVEL S_LEVEL , int basePrice )throws InvalidDrinkException{
        super(id,name,basePrice);
        validate(id);
        this.S_LEVEL = S_LEVEL;
    }
    @Override
    public int calculatePrice(){
        int tax = 0 ;
        switch(S_LEVEL){
            case HIGH :
                tax = 24;
                break;
            case LOW :
                tax = 18;
        }
        return getBasePrice() + tax ;
    }
    private void validate(String id) throws InvalidDrinkException{
        if(id.charAt(0) != 'D'){
            InvalidDrinkException e = new InvalidDrinkException();
            e.setMessage("ERROR! invalid drink id: " + id + " " + System.lineSeparator() +
                         "expected: 'D' but got '" + id.charAt(0) + "'");
            throw e ;
        }
    }
    @Override
    public String toString(){
        String SUGAR = "none";
        switch(S_LEVEL){
            case LOW:
                SUGAR = "low";
                break;
            case HIGH:
                SUGAR = "high";
        }
        return getID() +  delimiter + getName() + delimiter + SUGAR + delimiter + getBasePrice() ;
    }
}
