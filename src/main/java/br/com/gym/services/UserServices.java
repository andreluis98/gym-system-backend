package br.com.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.domain.Role;
import br.com.gym.model.User;
import br.com.gym.repository.UserRepository;


@Service
public class UserServices {

	  @Autowired
	    private UserRepository userRepository;
	  
	    public User register(User user) throws Exception {
	        if (userRepository.findByUsername(user.getUsername()) != null) {
	            throw new Exception("Username already exists.");
	        }
	        if (userRepository.findByEmail(user.getEmail()) != null) {
	            throw new Exception("Email already exists.");
	        }
	        if (user.getRole() == null) {
	            user.setRole(Role.ALUNO); 
	        }

	        return userRepository.save(user);
	    }
	  
	    public User login(String username, String password) {
	        return userRepository.findByUsernameAndPassword(username, password);
	    }
	    
	    public User findById(Long id) throws Exception {
	        return userRepository.findById(id)
	            .orElseThrow(() -> new Exception("Usuário não encontrado"));
	    }

}
