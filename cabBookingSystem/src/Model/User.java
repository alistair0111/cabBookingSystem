package Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String name;
    private int age;
    private Gender gender;
    private Location location;

    public User(String name, int age, Gender gender, Location location){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }
}
