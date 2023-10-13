package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getRoles();

    void addRole(Role role);

    List<Role> getRolesById(List<Integer> ids);
}
