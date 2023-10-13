package ru.kata.spring.boot_security.demo.repositories;


import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;



public interface RoleDao {

    List<Role> getRoles();

    void addRole(Role role);

    List<Role> getRolesById(List<Integer> ids);
}
