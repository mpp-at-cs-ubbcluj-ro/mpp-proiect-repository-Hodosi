package model;

public class TestParticipantRelation implements HasId<Tuple<Integer, Integer>> {
    Tuple<Integer, Integer> testParticipantID;

    public TestParticipantRelation(Tuple<Integer, Integer> testParticipantID) {
        this.testParticipantID = testParticipantID;
    }

    @Override
    public Tuple<Integer, Integer> getId() {
        return testParticipantID;
    }

    @Override
    public void setId(Tuple<Integer, Integer> id) {
        this.testParticipantID = id;
    }
}
