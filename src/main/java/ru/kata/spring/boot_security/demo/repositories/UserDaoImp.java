package ru.kata.spring.boot_security.demo.repositories;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;


import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public User showUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(showUser(id));
        entityManager.flush();
    }

    @Override
    public User findUserByName(String name) {
        try {
            Query query = entityManager.createQuery("from User where username=:paramName");
            return (User) query.setParameter("paramName", name).getSingleResult();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }
    @Override
    public List <Role> getRoles(){
        return entityManager.createQuery("from Role").getResultList();
    }
}
