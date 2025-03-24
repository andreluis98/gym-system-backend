package br.com.gym.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.model.TrainingPlan;
import br.com.gym.model.User;
import br.com.gym.model.Workout;
import br.com.gym.repository.TrainingPlanRepository;
import br.com.gym.repository.WorkoutRepository;

@Service
public class TrainingPlanService {

	@Autowired
	private TrainingPlanRepository trainingPlanRepository;
	
	@Autowired
	private WorkoutRepository workoutRepository;
	
	  public List<TrainingPlan> getTrainingPlansByUser(Long userId) {
	        return trainingPlanRepository.findByUserId(userId);
	    }
	  
	    public TrainingPlan createTrainingPlan(User user, List<Long> workoutIds) {
	        List<Workout> workouts = workoutRepository.findAllById(workoutIds);

	        TrainingPlan plan = new TrainingPlan();
	        plan.setUser(user);
	        plan.setWorkouts(workouts);

	        return trainingPlanRepository.save(plan);
	    }
	
	    public List<Workout> getAllWorkouts() {
	        return workoutRepository.findAll();
	    }
	
	    
	    public TrainingPlan getTrainingPlanWithWorkouts(Long planId) {
	        return trainingPlanRepository.findById(planId)
	            .map(trainingPlan -> {
	                trainingPlan.getWorkouts().forEach(workout -> workout.getExercises());
	                return trainingPlan;
	            })
	            .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
	    }

}
