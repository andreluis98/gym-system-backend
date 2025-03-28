package br.com.gym.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gym.model.Exercise;
import br.com.gym.services.ExerciseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/gym/exercises")
@Tag(name = "Exercise", description = "Endpoints para gerenciar os exercícios")
public class ExerciseController {
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Operation(
            summary = "Criar exercício",
            description = "Cria um novo exercício para um treino específico",
            responses = {
                @ApiResponse(responseCode = "201", description = "Exercício criado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Erro ao criar exercício")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createExercise(@RequestBody Exercise exercise, @RequestParam Long workoutId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Exercise savedExercise = exerciseService.createExercise(exercise, workoutId);

            response.put("status", "success");
            response.put("message", "Exercício criado com sucesso");
            response.put("exercise", savedExercise);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao criar exercício: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }        
}
