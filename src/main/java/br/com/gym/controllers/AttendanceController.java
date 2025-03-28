package br.com.gym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.gym.services.AttendanceService;
import jakarta.servlet.http.HttpSession;
import br.com.gym.dto.UserDTO;
import br.com.gym.model.Attendance;
import br.com.gym.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gym/attendances")
@Tag(name = "Attendance", description = "Endpoints para gerenciamento de presença")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Operation(
            summary = "Registrar presença do usuário",
            description = "Registra a presença de um usuário em um treino específico",
            responses = {
                @ApiResponse(responseCode = "200", description = "Presença registrada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Erro ao registrar presença"),
                @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
            }
    )
    @PostMapping("/mark")
    public ResponseEntity<Map<String, Object>> markAttendance(
            @RequestParam Long userId,
            @RequestParam Long workoutId,
            @RequestParam boolean isPresent,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");  

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Usuário não está logado!");
            return ResponseEntity.status(401).body(response); 
        }

        try {
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getUsername(), user.getRole().name());

            Attendance attendance = attendanceService.markAttendance(userId, workoutId, isPresent);
            response.put("status", "success");
            response.put("message", "Presença registrada com sucesso");
            response.put("attendance", attendance);
            response.put("user", userDTO);

            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao registrar presença: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    @Operation(
            summary = "Obter presença de um usuário em um treino",
            description = "Recupera a presença de um usuário em um treino específico, identificando por workoutId",
            responses = {
                @ApiResponse(responseCode = "200", description = "Presença encontrada com sucesso"),
                @ApiResponse(responseCode = "404", description = "Presença não encontrada"),
                @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
            }
    )
    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<Map<String, Object>> getAttendance(
            @PathVariable Long workoutId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Usuário não está logado!");
            return ResponseEntity.status(401).body(response); 
        }

        Long userId = user.getId(); 

        try {
            Optional<Attendance> attendance = attendanceService.getAttendance(userId, workoutId);

            if (attendance.isPresent()) {
                response.put("status", "success");
                response.put("attendance", attendance.get()); 
                return ResponseEntity.status(200).body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Presença não encontrada");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao buscar presença: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }
}
