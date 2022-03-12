package model;

public class TestAgeCategory extends Entity<Integer> {
    private final int minAge;
    private final int maxAge;

    public TestAgeCategory(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
