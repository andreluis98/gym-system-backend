package br.com.gym.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gym.model.Workout;
import br.com.gym.services.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/gym/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Operation(
            summary = "Obter todos os treinos",
            description = "Retorna uma lista com todos os treinos",
            responses = {
                @ApiResponse(responseCode = "200", description = "Treinos encontrados com sucesso"),
                @ApiResponse(responseCode = "404", description = "Nenhum treino encontrado")
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllWorkouts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Workout> workouts = workoutService.getAllWorkouts();

            response.put("status", "success");
            response.put("workouts", workouts);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao consultar os treinos: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Operation(
            summary = "Criar um novo treino",
            description = "Permite criar um treino",
            responses = {
                @ApiResponse(responseCode = "201", description = "Treino criado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do treino")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createWorkout(@RequestBody Workout workout) {
        Map<String, Object> response = new HashMap<>();
        try {
            Workout savedWorkout = workoutService.createWorkout(workout);

            response.put("status", "success");
            response.put("message", "Treino criado com sucesso");
            response.put("workout", savedWorkout);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao criar treino: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    @Operation(
            summary = "Obter treino por ID",
            description = "Recupera um treino específico pelo ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Treino encontrado com sucesso"),
                @ApiResponse(responseCode = "404", description = "Treino não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getWorkoutById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Workout workout = workoutService.getWorkoutById(id);

            if (workout != null) {
                response.put("status", "success");
                response.put("workout", workout);
                return ResponseEntity.status(200).body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Treino não encontrado");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao consultar treino: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
