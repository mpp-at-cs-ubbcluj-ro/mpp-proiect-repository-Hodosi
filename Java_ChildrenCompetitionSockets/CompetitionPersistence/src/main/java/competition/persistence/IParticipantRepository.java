package competition.persistence;


//public interface IParticipantRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
//    T findByUsername(String username);
//    Iterable<T> findAllParticipantsForTest(int id);
//}

import competition.model.Participant;

public interface IParticipantRepository extends IRepository<Integer, Participant> {
    Participant findByUsername(String username);
    Iterable<Participant> findAllParticipantsForTest(int id);
}
