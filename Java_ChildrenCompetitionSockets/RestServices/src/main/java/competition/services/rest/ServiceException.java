package competition.services.rest;

public class ServiceException extends RuntimeException {
    public ServiceException(Exception ex){
        super(ex);
    }

    public ServiceException(String message){
        super(message);
    }
}
