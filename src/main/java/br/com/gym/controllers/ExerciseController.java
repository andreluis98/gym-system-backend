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

@RestController
@RequestMapping("/gym/exercises")
public class ExerciseController {
	 	
		@Autowired
	    private ExerciseService exerciseService;
		
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
