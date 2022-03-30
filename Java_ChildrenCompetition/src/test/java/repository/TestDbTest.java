package repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDbTest {
    @Test
    @DisplayName("Test User Db")
    public void testTestDb(){
        assert (true);

        Properties properties = new Properties();
        try{
            properties.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        //DRAWING, TREASURE_HUNT, POETRY
        ITestRepository testRepository = new TestDbRepository(properties);
        model.Test test;
        test = testRepository.findOne(1);
        assertEquals(1, test.getId());
        assertEquals("DRAWING", test.getType().getType());
        assertEquals(6, test.getCategory().getMinAge());
        assertEquals(8, test.getCategory().getMaxAge());

        test = testRepository.findOne(2);
        assertEquals(2, test.getId());
        assertEquals("DRAWING", test.getType().getType());
        assertEquals(9, test.getCategory().getMinAge());
        assertEquals(11, test.getCategory().getMaxAge());

        test = testRepository.findOne(3);
        assertEquals(3, test.getId());
        assertEquals("DRAWING", test.getType().getType());
        assertEquals(12, test.getCategory().getMinAge());
        assertEquals(15, test.getCategory().getMaxAge());

        test = testRepository.findOne(4);
        assertEquals(4, test.getId());
        assertEquals("TREASURE_HUNT", test.getType().getType());
        assertEquals(6, test.getCategory().getMinAge());
        assertEquals(8, test.getCategory().getMaxAge());

        test = testRepository.findOne(5);
        assertEquals(5, test.getId());
        assertEquals("TREASURE_HUNT", test.getType().getType());
        assertEquals(9, test.getCategory().getMinAge());
        assertEquals(11, test.getCategory().getMaxAge());

        test = testRepository.findOne(6);
        assertEquals(6, test.getId());
        assertEquals("TREASURE_HUNT", test.getType().getType());
        assertEquals(12, test.getCategory().getMinAge());
        assertEquals(15, test.getCategory().getMaxAge());

        test = testRepository.findOne(7);
        assertEquals(7, test.getId());
        assertEquals("POETRY", test.getType().getType());
        assertEquals(6, test.getCategory().getMinAge());
        assertEquals(8, test.getCategory().getMaxAge());

        test = testRepository.findOne(8);
        assertEquals(8, test.getId());
        assertEquals("POETRY", test.getType().getType());
        assertEquals(9, test.getCategory().getMinAge());
        assertEquals(11, test.getCategory().getMaxAge());

        test = testRepository.findOne(9);
        assertEquals(9, test.getId());
        assertEquals("POETRY", test.getType().getType());
        assertEquals(12, test.getCategory().getMinAge());
        assertEquals(15, test.getCategory().getMaxAge());
    }
}
