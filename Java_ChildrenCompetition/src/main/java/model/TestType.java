package model;

public class TestType extends Entity<Integer>{
    //DRAWING, TREASURE_HUNT, POETRY
    private final String type;

    public TestType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
