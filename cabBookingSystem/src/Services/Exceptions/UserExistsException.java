package Services.Exceptions;

public class UserExistsException extends Exception{
    public UserExistsException(){
        super("User already exists");
    }
}
