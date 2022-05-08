package competition.model;

public class TestType extends Entity<Integer>{
    //DRAWING, TREASURE_HUNT, POETRY
    private String type;

    public TestType() {

    }
    public TestType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
