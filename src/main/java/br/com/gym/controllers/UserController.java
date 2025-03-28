package br.com.gym.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gym.dto.UserDTO;
import br.com.gym.model.User;
import br.com.gym.services.UserServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/gym/users")
@Tag(name = "User", description = "Endpoints para gerenciar usuários")
public class UserController {

    @Autowired
    private UserServices userService;

    @Operation(
            summary = "Registrar usuário",
            description = "Registra um novo usuário no sistema",
            responses = {
                @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Erro ao registrar usuário")
            }
    )
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
