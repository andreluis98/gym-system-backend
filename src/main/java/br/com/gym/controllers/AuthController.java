package br.com.gym.controllers;

import br.com.gym.domain.Role;
import br.com.gym.dto.LoginDTO;
import br.com.gym.dto.UserDTO;
import br.com.gym.model.User;
import br.com.gym.services.UserServices;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServices userService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        UserDTO userDTO = userService.login(loginDTO.getUsername(), loginDTO.getPassword());

        if (userDTO != null) {
        	User user = new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), Role.valueOf(userDTO.getRole()));
            session.setAttribute("user", user);

            response.put("status", "success");
            response.put("message", "Login bem-sucedido!");
            response.put("user", userDTO); 

            return ResponseEntity.status(200).body(response);
        } else {
            response.put("status", "error");
            response.put("message", "Usuário ou senha inválidos!");
            return ResponseEntity.status(401).body(response);
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "Logout realizado com sucesso!";
    }

    @GetMapping("/check-login")
    public String checkLogin(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "Usuário logado";
        } else {
            return "Usuário não está logado";
        }
    }
}
