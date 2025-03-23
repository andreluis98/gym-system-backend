package br.com.gym.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gym.model.TrainingPlan;
import br.com.gym.model.User;
import br.com.gym.services.TrainingPlanService;
import br.com.gym.services.UserServices;

@RestController
@RequestMapping("/gym/trainingplans")
public class TrainingPlanController {

	@Autowired
	private TrainingPlanService trainingPlanService;
	
	@Autowired
	private UserServices userServices;
	
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createTrainingPlan(@RequestParam Long userId, @RequestBody List<Long> workoutIds){
		Map<String, Object> response = new HashMap<>();
		try{
			User user = userServices.findById(userId);
			TrainingPlan plan = trainingPlanService.createTrainingPlan(user, workoutIds);
			
			response.put("status", "success");
			response.put("message", "Plano de treino criado com sucesso");
			response.put("plan", plan);
			return ResponseEntity.status(201).body(response);
		}catch(Exception e){
			response.put("status", "error");
			response.put("message", "Erro ao criar plano de treino: " + e.getMessage());
			return ResponseEntity.status(400).body(response);
		}
	}

}
