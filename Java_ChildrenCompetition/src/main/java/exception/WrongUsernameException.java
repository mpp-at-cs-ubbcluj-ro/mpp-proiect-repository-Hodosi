package exception;

public class WrongUsernameException extends RuntimeException{
    public WrongUsernameException(){
        super("invalid username");
    }
}
