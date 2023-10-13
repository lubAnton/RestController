package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;


@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private final EntityManager em;


    public RoleDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = em.createQuery("from Role").getResultList();
        return roles;
    }

    @Override
    public void addRole(Role role) {
        em.persist(role);
        em.flush();
    }

    @Override
    public List<Role> getRolesById(List<Integer> ids) {
        return em.createQuery("SELECT DISTINCT r FROM Role r WHERE r.id IN :ids", Role.class)
                .setParameter("ids", ids).getResultList();
    }
}
