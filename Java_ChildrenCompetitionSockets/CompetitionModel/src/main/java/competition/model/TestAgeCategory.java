package competition.model;

public class TestAgeCategory extends Entity<Integer> {
    private int minAge;
    private int maxAge;

    public TestAgeCategory(){

    }

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
