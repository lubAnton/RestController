package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> getRolesById(int id) {
        return roleDao.getRolesById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
