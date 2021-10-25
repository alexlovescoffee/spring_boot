package my.spring.spring_boot.service;

import my.spring.spring_boot.exception.servicelevel.DataNotSavedException;
import my.spring.spring_boot.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user) throws DataNotSavedException;
    void updateUser(User user) throws DataNotSavedException;
    void deleteUserById(long id) throws DataNotSavedException;
    User getUserById(long id);
    List<User> getAllUsers();
    User getUserByEmail(String name);
}
