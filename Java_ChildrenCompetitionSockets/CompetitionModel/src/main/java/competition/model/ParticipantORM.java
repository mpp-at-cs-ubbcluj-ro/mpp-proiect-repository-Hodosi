package competition.model;

public class ParticipantORM {
    private int id;
    private String username;
    private String name;
    private int age;

    public ParticipantORM(){

    }

    public ParticipantORM(String username, String name, int age) {
        this.username = username;
        this.name = name;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
