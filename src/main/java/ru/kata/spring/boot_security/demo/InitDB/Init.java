package ru.kata.spring.boot_security.demo.InitDB;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostConstruct
    @Transactional
    protected void initDB() {
        if (roleService.getRoles().size() == 0) {
            roleService.addRole(new Role("ROLE_ADMIN"));
            roleService.addRole(new Role("ROLE_USER"));
        }
        if (userService.getUsers().size()==0){
            User user = new User("try@mail.ru", "Ivanov", 33, "Andrey");
            user.setPassword("123");
            user.setRoles(roleService.getRoles());
            userService.saveUser(user);
        }

//
//        try {
//            userService.findUserByName(user.getUsername());
//        } catch (UsernameNotFoundException e) {
//            userService.saveUser(user);
//        }

    }
}
