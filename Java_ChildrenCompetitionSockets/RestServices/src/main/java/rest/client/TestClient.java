package rest.client;

import competition.model.Test;
import competition.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class TestClient {

    public static final String URL = "http://localhost:8088/competition/tests";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable){
        try{
            return callable.call();
        }catch (ResourceAccessException | HttpClientErrorException ex) { // server down, resource exceptio
            throw new ServiceException(ex);
        }
        catch (Exception ex){
            throw new ServiceException(ex);
        }
    }

    public Test[] getAll(){
        return execute(() -> restTemplate.getForObject(URL, Test[].class));
    }

    public Test getById(String id){
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Test.class));
    }

    public Test create(Test test){
        return execute(() -> restTemplate.postForObject(URL, test, Test.class));
    }

    public void update(Test test){
        execute(() -> {
           restTemplate.put(String.format("%s/%s", URL, test.getId()), test);
           return null;
        });
    }

    public void delete(String id){
        execute(() -> {
           restTemplate.delete(String.format("%s/%s", URL, id));
           return null;
        });
    }
}
