package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user);

    User getUserInfo(int id);

    void editUser(User user);

    void deleteUser(int id);

    User findUserByName(String name);

    User converToUser(UserDTO userDTO);

}