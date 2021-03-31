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
