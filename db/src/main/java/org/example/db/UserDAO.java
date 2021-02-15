package org.example.db;

import java.util.List;

public interface UserDAO {
    User create(User u);
    List<User> getAll();

}
