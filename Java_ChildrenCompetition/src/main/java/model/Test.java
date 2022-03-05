package model;

import repository.HasId;

public class Test implements HasId<Integer> {
    private int testID;
    private final TestType type;
    private final TestAgeCategory category;

    public Test(int testID, TestType type, TestAgeCategory category) {
        this.testID = testID;
        this.type = type;
        this.category = category;
    }

    public TestType getType() {
        return type;
    }

    public TestAgeCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testID=" + testID +
                ", type=" + type +
                ", category=" + category +
                '}';
    }

    @Override
    public Integer getId() {
        return testID;
    }

    @Override
    public void setId(Integer id) {
        this.testID = id;
    }
}
