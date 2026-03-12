package org.example.repository;

import org.example.entities.Account;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByUser(User user);

}