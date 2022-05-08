package competition.services.rest;

import competition.model.Test;
import competition.persistence.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competition/tests")
public class CompetitionTestController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ITestRepository testRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return String.format(template, name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Test[] getAll(){
        System.out.println("Get all tests...");
        List<Test> testList = (List<Test>) testRepository.findAll();
        return testList.toArray(new Test[testList.size()]);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id " + id);
        Test test = testRepository.findOne(Integer.valueOf(id));
        if(test == null){
            return new ResponseEntity<String>("Test not found ", HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<Test>(test, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Test create(@RequestBody Test test) {
        System.out.println("Saving test...");
        testRepository.save(test);
        return test;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Test update(@RequestBody Test test){
        System.out.println("Updating test");
        testRepository.update(test.getId(), test);
        return test;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting test..." + id);
        try{
            testRepository.delete(Integer.parseInt(id));
            return new ResponseEntity<Test>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Current delete test exeception");
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String testError(Exception ex){
        return ex.getMessage();
    }

}
