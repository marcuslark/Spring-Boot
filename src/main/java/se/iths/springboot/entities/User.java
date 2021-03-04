package se.iths.springboot.entities;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="users")
@NoArgsConstructor
public class User {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String firstName;
    private String lastName;


    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User id=" + id + ", name = " + firstName + " " + lastName;
    }
}
