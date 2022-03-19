package exception;

public class TestLimitException extends RuntimeException{
    public TestLimitException(){
        super("you already joined for two test");
    }
}
