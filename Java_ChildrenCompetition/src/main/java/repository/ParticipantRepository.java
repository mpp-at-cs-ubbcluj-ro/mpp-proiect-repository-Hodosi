package repository;

import model.Participant;

public class ParticipantRepository extends AbstractRepository<Integer, Participant>{
    public ParticipantRepository(Validator<Participant> validator){
        super(validator);
    }
}
