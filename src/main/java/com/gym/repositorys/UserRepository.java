package com.gym.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.models.User;


public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    User findByEmail(String email);
}
