package br.com.gym.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gym.model.User;
import br.com.gym.services.UserServices;

@RestController
@RequestMapping("/gym/users")
public class UserController {
	
	@Autowired
    private UserServices userService;
	
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            User newUser = userService.register(user);
            response.put("status", "success");
            response.put("message", "Usuário registrado com sucesso");
            response.put("user", newUser); 
            return ResponseEntity.status(201).body(response); 
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao registrar usuário: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

	
    @PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user){
		System.out.printf("USER: ", user);
		Map<String, Object> response = new HashMap<>();
		 try {
	            User loggedInUser = userService.login(user.getUsername(), user.getPassword());
	            if (loggedInUser != null) {
	                response.put("status", "success");
	                response.put("message", "Login realizado com sucesso");
	                response.put("id", loggedInUser.getId());
	                response.put("username", loggedInUser.getUsername());

	                return ResponseEntity.status(200).body(response); 
	            } else {
	                response.put("status", "error");
	                response.put("message", "Credenciais inválidas");
	                return ResponseEntity.status(401).body(response); 
	            }
	        } catch (Exception e) {
	            response.put("status", "error");
	            response.put("message", "Erro ao processar login: " + e.getMessage());
	            return ResponseEntity.status(500).body(response); 
	        }
	}

}
