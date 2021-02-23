package se.iths.springboot.db;

import java.util.List;

public interface UserDAO {

    User getByFirstName (String firstName);

    List<User> getAll();

    User getById(int id);

    User getByFirstLast(String firstName, String lastName);

    User add(String firstName, String lastName);

    User removeByFirstName(String firstName);

    User removeByLastName(String lastName);

    User removeById(int id);

    User updateByFirstLast(String firstName, String lastName, String newFirstName, String newLastName);
}
