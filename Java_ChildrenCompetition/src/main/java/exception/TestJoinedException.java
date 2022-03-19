package exception;

public class TestJoinedException extends RuntimeException{
    public TestJoinedException(){
        super("you already joined for this test");
    }
}
