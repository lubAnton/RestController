package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.repositories.UserDao;


@Service
public class RegistrationServiceImpl {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    private final RoleDao roleDao;

    @Autowired
    public RegistrationServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Transactional
    public void registration(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(roleDao.findByName("ROLE_USER"));
        userDao.save(user);
    }

   @Transactional
    public void adminRegistration (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(roleDao.findByName("ROLE_ADMIN"));
        user.addRole(roleDao.findByName("ROLE_USER"));
        userDao.save(user);
    }
}
