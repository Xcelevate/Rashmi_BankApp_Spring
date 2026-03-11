package org.example.service;

import org.example.dao.*;
import org.example.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankingService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    public User login(int id, String password) {
        return userDAO.login(id, password);
    }

    @Transactional
    public Account createAccount(User user) {
        Account acc = new Account();
        acc.setUser(user);
        acc.setBalance(0);
        accountDAO.save(acc);
        return acc;
    }

    public List<Account> getAccounts(User user) {
        return accountDAO.findByUser(user);
    }

    @Transactional
    public void deposit(int accId, double amount) {
        Account acc = accountDAO.findById(accId);
        acc.setBalance(acc.getBalance() + amount);

        Transaction tx = new Transaction();
        tx.setType("DEPOSIT");
        tx.setAmount(amount);
        tx.setAccount(acc);
        transactionDAO.save(tx);
    }


    @Transactional
    public void withdraw(int accId, double amount) {
        Account acc = accountDAO.findById(accId);

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        acc.setBalance(acc.getBalance() - amount);

        Transaction tx = new Transaction();
        tx.setType("WITHDRAW");
        tx.setAmount(amount);
        tx.setAccount(acc);
        transactionDAO.save(tx);
    }

    @Transactional
    public void transfer(int fromId, int toId, double amount) {
        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    @Transactional
    public User createUser(String name, String pwd) {
        User user = new User();
        user.setName(name);
        user.setPassword(pwd);

        userDAO.save(user);
        return user;
    }
}
