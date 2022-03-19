package model;

public class TestDTO extends Entity<Integer> {
    private final String testType;
    private final String testAgeCategory;
    private int noCompetitors;

    public TestDTO(String testType, String testAgeCategory, int noCompetitors) {
        this.testType = testType;
        this.testAgeCategory = testAgeCategory;
        this.noCompetitors = noCompetitors;
    }

    public String getTestType() {
        return testType;
    }

    public String getTestAgeCategory() {
        return testAgeCategory;
    }

    public int getNoCompetitors() {
        return noCompetitors;
    }
}
