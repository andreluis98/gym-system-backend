package br.com.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.domain.Role;
import br.com.gym.dto.UserDTO;
import br.com.gym.model.User;
import br.com.gym.repository.UserRepository;


@Service
public class UserServices {

	  @Autowired
	    private UserRepository userRepository;
	  
	    public UserDTO login(String username, String password) {
	        User user = userRepository.findByUsernameAndPassword(username, password);  
	        if (user != null) {
	            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getUsername(), user.getRole().name());
	        } else {
	            return null;
	        }
	    }
  
	    public UserDTO register(User user) throws Exception {
	        if (userRepository.findByUsername(user.getUsername()) != null) {
	            throw new Exception("Username already exists.");
	        }
	        if (userRepository.findByEmail(user.getEmail()) != null) {
	            throw new Exception("Email already exists.");
	        }
	        if (user.getRole() == null) {
	            user.setRole(Role.ALUNO); 
	        }
	        user = userRepository.save(user);

	        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getUsername(), user.getRole().name());
	    }
	  
	    
	    public User findById(Long id) throws Exception {
	        return userRepository.findById(id)
	                .orElseThrow(() -> new Exception("Usuário não encontrado"));
	    }
	    
	    public User validateUser(String username, String password) {
	        return userRepository.findByUsernameAndPassword(username, password);
	    }
}
