package ru.kata.spring.boot_security.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDao userDao, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByName(username);

        if (user == null) {
            new UsernameNotFoundException("User is not found");
        }
        return user;
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }


    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional
    public void editUser(User user) {
        if (!getUserInfo(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.editUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        if (userDao.showUser(id) != null) {
            userDao.deleteUser(id);
        }
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public User getUserInfo(int id) {
        return userDao.showUser(id);
    }

    @Override
    @Transactional
    public User converToUser(UserDTO userDTO) {
        ModelMapper md = new ModelMapper();
        return md.map(userDTO, User.class);
    }
}
