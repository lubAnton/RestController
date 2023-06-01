package ru.kata.spring.boot_security.demo.repositories;



import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;


import java.util.List;

public interface UserDao {

    List<User> getUsers();
    void save(User user);
    User showUser(int id);
    void editUser(User user);
    void deleteUser(int id);
    User findUserByName (String name);
    List <Role> getRoles();

}
