package br.com.gym.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gym.dto.UserDTO;
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
            UserDTO newUserDTO = userService.register(user);  
            
            response.put("status", "success");
            response.put("message", "Usuário registrado com sucesso");
            response.put("user", newUserDTO);
            return ResponseEntity.status(201).body(response);  
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao registrar usuário: " + e.getMessage());
            return ResponseEntity.status(400).body(response);  
        }
    }

}
