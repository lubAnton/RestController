package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.naming.NameNotFoundException;
import javax.persistence.NoResultException;

@Component
public class UserValidator implements Validator {
    private final UserServiceImpl userService;
    private final UserDao userDao;

    public UserValidator(UserServiceImpl userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userDao.findUserByName(user.getUsername());
        } catch (UsernameNotFoundException e) {
            return;
        }
        errors.rejectValue("username", "", "Пользователь с таким именем существует");
    }
}