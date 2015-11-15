package exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String username){
        super(username+" already registered");
    }
}
