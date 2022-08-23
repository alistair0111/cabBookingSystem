package Services.Exceptions;

public class DriverAlreadyExists extends Exception{
    public DriverAlreadyExists(){
        super("Driver already exists");
    }
}
