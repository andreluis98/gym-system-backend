package br.com.gym.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.model.Workout;
import br.com.gym.repository.WorkoutRepository;

@Service
public class WorkoutService {

	@Autowired
    private WorkoutRepository workoutRepository;
	
	public List<Workout> getWorkoutsByCategory(String category) {
        return workoutRepository.findByCategory(category);
    }
	
	public List<Workout> searchWorkoutsByName(String name) {
        return workoutRepository.findByNameContainingIgnoreCase(name);
    }

}
