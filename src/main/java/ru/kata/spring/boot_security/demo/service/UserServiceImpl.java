package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            new UsernameNotFoundException("User is not found");
        }
        return user;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Transactional
    public void editUser (User user) {
        user.setPassword(loadUserByUsername(user.getUsername()).getPassword());
        user.setRoles((loadUserByUsername(user.getUsername()).getRoles()));
            userRepository.save(user);
    }
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteUser (int id) {
        if (userRepository.getById(id)!=null)
        userRepository.deleteById(id);
    }
    public User getUserInfo (int id) {
        return userRepository.getById(id);
    }
    public String getCurrentName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public int getCurrentId() {
        return loadUserByUsername(getCurrentName()).getId();
    }
}
