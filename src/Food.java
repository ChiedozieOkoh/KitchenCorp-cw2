/*-------------------------------------------------------------------------------------------
 *   NAME: Drink.java
 *   DATE: 20th April.java
 *   AUTHOR: Chiedozie Okoh
 *
 *   SUMMARY: Implentation of Product class. Encapsulates calculating the price of Food
 *            and serialising food objects
 *
 *   NOTES:
 *       inherits from Product
 *
 *   PUBLIC FIELDS:
 *       ENUMERATE: FOOD_TYPE
 *-------------------------------------------------------------------------------------------*/
public class Food extends Product {
    public enum FOOD_TYPE {
        HOT,
        COLD
    }
    public static final float  SURCHARGE = 1.1f;
    private final FOOD_TYPE TYPE  ;
    public Food(String id , String name , FOOD_TYPE type , int basePrice ) throws InvalidFoodException{
        super (id,name,basePrice);
        validate(id);
        this.TYPE = type ;

    }
    @Override
    public int calculatePrice(){
        float tax = 1.0f;

        if (TYPE == FOOD_TYPE.HOT){
            tax = SURCHARGE;
        }
        return Math.round((float)getBasePrice() * tax);
    }
    public  FOOD_TYPE getType(){
        if (TYPE == FOOD_TYPE.HOT){
            return FOOD_TYPE.HOT ;
        }
        return FOOD_TYPE.COLD;
    }

    private void validate( String id  )throws InvalidFoodException{
        if(id.charAt(0) != 'F'){
            InvalidFoodException e = new InvalidFoodException();
            e.setMessage("ERROR! food ID: " + id + " is invalid" + System.lineSeparator() +
                         "expected: 'F' but got '" +id.charAt(0)+"'");
            throw e;
        }

    }
    @Override
    public String toString(){
        String temp = "hot";
        if (TYPE == FOOD_TYPE.COLD){
            temp = "cold";
        }
        return getID() + delimiter + getName() + delimiter + temp + delimiter + getBasePrice();
    }

}
