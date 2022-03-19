package exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(){
        super("invalid password");
    }
}
