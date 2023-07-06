package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;


    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public List<Role> getRolesById(List<Integer> ids) {
        return roleDao.getRolesById(ids);
    }
}
