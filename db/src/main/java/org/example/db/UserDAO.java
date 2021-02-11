package org.example.db;

import java.util.List;

public interface UserDAO {

    List<User> getAll();
    List<User> getByUserName(String userName);
    List<User> getByFirstName(String firstName);
    List<User> getByLastName(String lastName);
    User getById(String id);

}
