package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    void add(User username);

    void delete(Long id);

    User getUserByName(String username);
    User getUserById(long id);

    void upDateUser(User username);

    List<User> getAllUsers();
}
