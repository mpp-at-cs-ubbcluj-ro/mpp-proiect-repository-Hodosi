package competition.model;

public class Test extends Entity<Integer> {
    private TestType type;
    private TestAgeCategory category;

    public Test(){

    }

    public Test(TestType type, TestAgeCategory category) {
        this.type = type;
        this.category = category;
    }

    public TestType getType() {
        return type;
    }

    public TestAgeCategory getCategory() {
        return category;
    }
}
