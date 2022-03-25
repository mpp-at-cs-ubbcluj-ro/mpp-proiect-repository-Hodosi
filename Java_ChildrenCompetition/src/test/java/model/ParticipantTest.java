package model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantTest {

    @Test
    @DisplayName("Test Participant")
    public void testParticipant(){
        int id = 1;
        String username = "A";
        String name = "A";
        int age = 10;
        Participant participant = new Participant(username, name, age);
        participant.setId(id);
        assertEquals(id, participant.getId());
        assertEquals(name, participant.getName());
        assertEquals(age, participant.getAge());
        assertEquals(username, participant.getUsername());
    }
}
