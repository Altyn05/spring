package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    void delete(long id);

    User getUserById(long id);

    User getUserByName(String username);

    void upDateUser(User user);

    List<User> getAllUsers();

}
