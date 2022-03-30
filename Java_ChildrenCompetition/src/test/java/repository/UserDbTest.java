package repository;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDbTest {
    @Test
    @DisplayName("Test User Db")
    public void testUserDb(){
        assert (true);

        Properties properties = new Properties();
        try{
            properties.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        IUserRepository userRepository = new UserDbRepository(properties);
        User user = userRepository.findOne(1);
        assertEquals(1, user.getId());
        assertEquals("testFirstname", user.getFirstname());
        assertEquals("testLastname", user.getLastname());
        assertEquals("testUsername", user.getUsername());
        assertEquals("testPassword", user.getPassword());

        user = userRepository.findByUsername("testUsername");
        assertEquals(1, user.getId());
        assertEquals("testFirstname", user.getFirstname());
        assertEquals("testLastname", user.getLastname());
        assertEquals("testUsername", user.getUsername());
        assertEquals("testPassword", user.getPassword());
    }
}
