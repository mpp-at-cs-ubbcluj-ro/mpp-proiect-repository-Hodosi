package exception;

public class InvalidParticipantAgeException extends RuntimeException{
    public InvalidParticipantAgeException(){
        super("user already exists with different age");
    }
}
