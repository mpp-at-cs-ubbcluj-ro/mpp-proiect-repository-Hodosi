package model;

import repository.HasId;

public class Participant implements HasId<Integer> {
    private int participantID;
    private final String name;
    private final int age;

    public Participant(int participantID, String name, int age) {
        this.participantID = participantID;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "participantID=" + participantID +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public Integer getId() {
        return participantID;
    }

    @Override
    public void setId(Integer id) {
        this.participantID = id;
    }
}
