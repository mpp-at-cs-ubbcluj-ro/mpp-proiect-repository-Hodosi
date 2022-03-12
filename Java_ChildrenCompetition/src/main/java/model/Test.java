package model;

public class Test extends Entity<Integer> {
    private final TestType type;
    private final TestAgeCategory category;

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
