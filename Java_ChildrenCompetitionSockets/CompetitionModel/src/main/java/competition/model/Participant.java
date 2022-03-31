package competition.model;

public class Participant extends Entity<Integer> {
    private final String username;
    private final String name;
    private final int age;

    public Participant(String username, String name, int age) {
        this.username = username;
        this.name = name;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
