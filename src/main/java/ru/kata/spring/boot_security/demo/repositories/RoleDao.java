package ru.kata.spring.boot_security.demo.repositories;


import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Set;


public interface RoleDao {
    Set<Role> getRolesById (int id);
    List<Role> getRoles();
}
