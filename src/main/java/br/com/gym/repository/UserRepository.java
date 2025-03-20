package br.com.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gym.model.User;

public interface UserRepository  extends JpaRepository<User, Long>  {
	User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    User findByEmail(String email);
}
