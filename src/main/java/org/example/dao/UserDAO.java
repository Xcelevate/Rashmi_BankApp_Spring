package org.example.dao;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.example.Entities.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public User login(int id, String password) {
        try {
            User user = em.find(User.class, id);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception ignored) {}
        return null;
    }
    @Transactional
    public void save(User user) {
        em.persist(user);
    }

}


