package repository;

import model.TestParticipantRelation;
import model.Tuple;

public class TestParticipantRelationRepository extends AbstractRepository<Tuple<Integer, Integer>, TestParticipantRelation>{
    public TestParticipantRelationRepository(Validator<TestParticipantRelation> validator){
        super(validator);
    }
}
