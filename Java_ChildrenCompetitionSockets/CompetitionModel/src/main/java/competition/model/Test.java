package competition.model;

public class Test extends model.Entity<Integer> {
    private final model.TestType type;
    private final model.TestAgeCategory category;

    public Test(model.TestType type, model.TestAgeCategory category) {
        this.type = type;
        this.category = category;
    }

    public model.TestType getType() {
        return type;
    }

    public model.TestAgeCategory getCategory() {
        return category;
    }
}
