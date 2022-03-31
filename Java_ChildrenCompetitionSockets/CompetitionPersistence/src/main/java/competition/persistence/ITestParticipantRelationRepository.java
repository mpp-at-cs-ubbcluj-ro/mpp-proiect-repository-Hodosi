package competition.persistence;

import model.TestParticipantRelation;
import model.Tuple;

//public interface ITestParticipantRelationRepository<ID, T extends HasId<ID>> extends IRepository<ID, T> {
//
//}

public interface ITestParticipantRelationRepository extends repository.IRepository<Tuple<Integer, Integer>, TestParticipantRelation> {

}