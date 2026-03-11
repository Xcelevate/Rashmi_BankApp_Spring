package org.example.dao;


import jakarta.persistence.*;
import org.example.Entities.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(Transaction tx) {
        em.persist(tx);
    }
}


