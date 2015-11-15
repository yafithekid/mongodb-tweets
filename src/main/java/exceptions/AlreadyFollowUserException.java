package exceptions;

public class AlreadyFollowUserException extends Exception {
    public AlreadyFollowUserException(String followed){
        super("already followed "+followed);
    }
}
