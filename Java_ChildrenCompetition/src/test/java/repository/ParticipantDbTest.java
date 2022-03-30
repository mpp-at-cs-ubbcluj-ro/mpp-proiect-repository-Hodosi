package repository;

import model.Participant;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantDbTest {
    @Test
    @DisplayName("Test Participant Db")
    public void testUserDb(){
        assert (true);

        Properties properties = new Properties();
        try{
            properties.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        IParticipantRepository participantRepository = new ParticipantDbRepository(properties);
        Participant participant = participantRepository.findByUsername("t1");
        assertEquals(1, participant.getId());
        assertEquals("test1", participant.getName());
        assertEquals(6, participant.getAge());
    }
}
