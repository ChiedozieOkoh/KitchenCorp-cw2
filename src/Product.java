/*-------------------------------------------------------------------------------------------
 *   NAME: Product.java
 *   DATE: 20th April.java
 *   AUTHOR: Chiedozie Okoh
 *
 *   SUMMARY: encapsulates behaviour for calculating price of products
 *
 *
 *
 *
 *
 *
 *-------------------------------------------------------------------------------------------*/
public abstract  class Product {
    public static final char delimiter = '@';
    private final String id ;
    private final int basePrice;
    private final String name;
    public Product(String id , String name , int basePrice)throws InvalidProductException{
        validate(id , basePrice);
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;

    }
    abstract  int calculatePrice();
    public String getID(){
        return id;
    }
    public String getName(){
        return name;
    }

    private void validate (String id , int basePrice) throws InvalidProductException{
        final int requiredLength = 9;

        if(id.length() != requiredLength){
            InvalidProductException e = new InvalidProductException();
            e.setMessage("ERROR! invalid productID: " + id +" "+ System.lineSeparator() +
                          "ID must be 9 characters long");
            throw e;
        }
        if (id.charAt(1) != '-'){
            InvalidProductException e = new InvalidProductException();
            e.setMessage("ERROR! invalid productID: " + id + " "+ System.lineSeparator() +
                         "invalid character " + System.lineSeparator()+
                         "expected: '-' but found '" + id.charAt(1) + "'");
            throw e;
        }
        for (int i = 2 ; i < requiredLength - 1 ; i ++){
            if(!Character.isDigit(id.charAt(i))){
                InvalidProductException e = new InvalidProductException();
                e.setMessage("ERROR! invalid productID: " + id + " "+ System.lineSeparator() +
                             "character '"+ id.charAt(i)+"'" + "is invalid and should be numeric");
                throw e;
            }

        }
        if(basePrice < 0){
            InvalidProductException e = new InvalidProductException();
            e.setMessage("ERROR! invalid product: " + id + " " + System.lineSeparator() +
                         "product price cannot be negative");
        }
    }
    public int getBasePrice(){
        return basePrice;
    }
}
