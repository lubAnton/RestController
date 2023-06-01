package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{
    private final EntityManager em;

    @Autowired
    public RoleDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Set<Role> getRolesById(int id) {
        return Collections.singleton(em.find(Role.class, id));
    }
    @Override
    public Role findByName(String name) {
        Query query = em.createQuery("from Role as role where role.name=:paramName");
        System.out.println("findByName");
        try {
            return  (Role) query.setParameter("paramName", name).getSingleResult();
        } catch (NoResultException e) {
            Role role = new Role(name);
            em.persist(role);
            em.flush();
            return role;
        }
    }
}
