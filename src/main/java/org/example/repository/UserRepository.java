package org.example.repository;

import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByIdAndPassword(int id, String password);

}