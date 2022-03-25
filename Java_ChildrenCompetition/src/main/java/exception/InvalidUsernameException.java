package exception;

public class InvalidUsernameException extends RuntimeException{
    public InvalidUsernameException(){
        super("invalid username, please choose other");
    }
}
