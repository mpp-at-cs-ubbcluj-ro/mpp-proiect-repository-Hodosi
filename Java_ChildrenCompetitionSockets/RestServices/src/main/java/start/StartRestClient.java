package start;

import competition.model.Test;
import competition.model.TestAgeCategory;
import competition.model.TestType;
import competition.services.rest.ServiceException;
import rest.client.TestClient;

import java.util.concurrent.atomic.AtomicInteger;

public class StartRestClient {
    private final static TestClient testsClient = new TestClient();

    public static void main(String[] args) {
        TestType testType = new TestType("testClient");
        testType.setId(70);
        TestAgeCategory ageCategory = new TestAgeCategory(22,52);
        ageCategory.setId(70);
        Test newTest = new Test(testType, ageCategory);

        try {
            show(()->{
                Test[] tests = testsClient.getAll();
                for(Test t : tests){
                    System.out.println(t.getId());
                }
            });
            show(()->{
                int id = 1;
                System.out.println(testsClient.getById(String.valueOf(id)));
            });
            show(()->{
                Test created = testsClient.create(newTest);
                System.out.println(created.getId());
            });
            show(()->{
                testType.setId(3);
                ageCategory.setId(4);
                Test testUpdate = new Test(testType, ageCategory);
                testUpdate.setId(19);
                testsClient.update(testUpdate);
            });
            show(()-> {
                testsClient.delete(String.valueOf(21));
            });
        }catch (Exception ex){
            System.out.println("Exception test client: " + ex.getMessage());
        }

    }

    private static void show(Runnable task){
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
