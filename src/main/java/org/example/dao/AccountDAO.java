package org.example.dao;

import jakarta.transaction.Transactional;
import org.example.Entities.Account;


import jakarta.persistence.*;
import org.example.Entities.Transaction;
import org.example.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(Account acc) {

        em.persist(acc);
    }

    public Account findById(int id) {

        return em.find(Account.class, id);
    }

    public List<Account> findByUser(User user) {
        return em.createQuery(
                        "FROM Account WHERE user = :u", Account.class)
                .setParameter("u", user)
                .getResultList();
    }
}



