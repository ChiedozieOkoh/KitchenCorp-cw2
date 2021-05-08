/*-------------------------------------------------------------------------------------------
 *   NAME: SimulationException.java
 *   DATE: 20th April.java
 *   AUTHOR: Chiedozie Okoh
 *
 *   SUMMARY: Acts as an accessor and mutator for any given error message.
 *
 *
 *-------------------------------------------------------------------------------------------*/
public class SimulationException extends RuntimeException{
    private String message;
    public SimulationException(){

    }
    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }
}
