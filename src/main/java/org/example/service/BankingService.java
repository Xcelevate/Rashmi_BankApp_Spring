package org.example.service;

import org.example.entities.*;
import org.example.repository.*;
import org.example.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankingService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository txRepo;

    public User login(int id,String password){

        User user = userRepo.findByIdAndPassword(id,password);

        if(user == null)
            throw new UserNotFoundException("Invalid credentials");

        return user;
    }

    @Transactional
    public User createUser(String name,String pwd){

        User user = new User();
        user.setName(name);
        user.setPassword(pwd);

        return userRepo.save(user);
    }

    @Transactional
    public Account createAccount(User user){

        Account acc = new Account();
        acc.setUser(user);
        acc.setBalance(0);

        return accountRepo.save(acc);
    }

    public List<Account> getAccounts(User user){
        return accountRepo.findByUser(user);
    }

    @Transactional
    public void deposit(int accId,double amount){

        Account acc = accountRepo.findById(accId).orElseThrow();

        acc.setBalance(acc.getBalance()+amount);

        Transaction tx = new Transaction();
        tx.setType("DEPOSIT");
        tx.setAmount(amount);
        tx.setAccount(acc);

        txRepo.save(tx);
    }

    @Transactional
    public void withdraw(int accId,double amount){

        Account acc = accountRepo.findById(accId).orElseThrow();

        if(acc.getBalance()<amount)
            throw new InsufficientBalanceException("Insufficient balance");

        acc.setBalance(acc.getBalance()-amount);

        Transaction tx = new Transaction();
        tx.setType("WITHDRAW");
        tx.setAmount(amount);
        tx.setAccount(acc);

        txRepo.save(tx);
    }

    @Transactional
    public void transfer(int fromId,int toId,double amount){

        withdraw(fromId,amount);
        deposit(toId,amount);
    }
}