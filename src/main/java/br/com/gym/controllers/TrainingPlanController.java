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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gym.model.TrainingPlan;
import br.com.gym.model.User;
import br.com.gym.services.TrainingPlanService;
import br.com.gym.services.UserServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/gym/trainingplans")
@Tag(name = "TrainingPlan", description = "Endpoints para gerenciar planos de treino")
public class TrainingPlanController {

    @Autowired
    private TrainingPlanService trainingPlanService;
    
    @Autowired
    private UserServices userServices;
    
    @Operation(
            summary = "Criar plano de treino",
            description = "Cria um novo plano de treino para um usuário, associando-o a vários treinos",
            responses = {
                @ApiResponse(responseCode = "201", description = "Plano de treino criado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Erro ao criar plano de treino")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createTrainingPlan(@RequestParam Long userId, @RequestBody List<Long> workoutIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userServices.findById(userId);
            TrainingPlan plan = trainingPlanService.createTrainingPlan(user, workoutIds);

            response.put("status", "success");
            response.put("message", "Plano de treino criado com sucesso");
            response.put("plan", plan);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao criar plano de treino: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }
    
    @Operation(
            summary = "Obter plano de treino por ID",
            description = "Recupera um plano de treino específico pelo ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Plano de treino encontrado com sucesso"),
                @ApiResponse(responseCode = "404", description = "Plano de treino não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTrainingPlanById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            TrainingPlan plan = trainingPlanService.getTrainingPlanWithWorkouts(id);

            response.put("status", "success");
            response.put("plan", plan);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Plano de treino não encontrado: " + e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }
}
