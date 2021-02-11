package org.example.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDAO {

    private Connection connection;
    PreparedStatement findAll, findByUserName, findByFirstName, findByLastName, findById;

    public UserDaoJDBCImpl() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;username=java;password=password;database=everyloop");
            findAll = connection.prepareStatement("select * from users");
            findByUserName = connection.prepareStatement("select * from users where userName like ?");
            findByFirstName = connection.prepareStatement("select * from users where firstName like ?");
            findByLastName = connection.prepareStatement("select * from users where lastName like ?");
            findById = connection.prepareStatement("select * from users where id like ?");
        } catch (SQLException e) {
            throw new RuntimeException("UserDao constructor problem: " + e);
        }
    }

    public List<User> listFromStatement(PreparedStatement stmt) throws SQLException {
        List<User> list = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            list.add(new User(resultSet.getString("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("email"),
                    resultSet.getString("phone")));
        }
        return list;
    }

    @Override
    public List<User> getAll() {
        try {
            return listFromStatement(findAll);
        } catch (SQLException e) {
            throw new RuntimeException("UserDao findAll problem: " + e);
        }
    }

    @Override
    public List<User> getByUserName(String userName) {
        try {
            findByUserName.setString(1, userName);
            return listFromStatement(findByUserName);
        } catch (SQLException e) {
            throw new RuntimeException("UserDao findByUserName problem: " + e);
        }
    }

    @Override
    public List<User> getByFirstName(String firstName) {
        try {
            findByUserName.setString(1, firstName);
            return listFromStatement(findByFirstName);
        } catch (SQLException e) {
            throw new RuntimeException("UserDao findByFirstName problem: " + e);
        }
    }

    @Override
    public List<User> getByLastName(String lastName) {
        try {
            findByUserName.setString(1, lastName);
            return listFromStatement(findByLastName);
        } catch (SQLException e) {
            throw new RuntimeException("UserDao findByLastName problem: " + e);
        }
    }

    @Override
    public User getById(String id) {
        try {
            findById.setString(1, id);
            return (User) listFromStatement(findById);
        } catch (SQLException e) {
            throw new RuntimeException("UserDao findById problem: " + e);
        }
    }
}
