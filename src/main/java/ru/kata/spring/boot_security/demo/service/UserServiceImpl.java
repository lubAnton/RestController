package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserDao userDao;
    private final RoleService roleService;


    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByName(username);

        if (user == null) {
            new UsernameNotFoundException("User is not found");
        }
        return user;
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<User> getUsers(){
        return userDao.getUsers();
    }


    @Transactional
    public void saveUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        for (Role role: user.getRoles()) {
            user.setRoles(roleService.getRolesById(Integer.parseInt(role.getName())));
        }
        userDao.save(user);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Transactional
    public void editUser (User user) {
        for (Role role: user.getRoles()) {
            user.setRoles(roleService.getRolesById(Integer.parseInt(role.getName())));
        }
        System.out.println(user.toString());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.editUser(user);
    }
    @Override
    @Transactional
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteUser (int id) {
        if (userDao.showUser(id)!=null)
        userDao.deleteUser(id);
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public User getUserInfo (int id) {
        return userDao.showUser(id);
    }
}
