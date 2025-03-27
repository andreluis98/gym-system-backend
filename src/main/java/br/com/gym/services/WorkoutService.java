package br.com.gym.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.model.Exercise;
import br.com.gym.model.Workout;
import br.com.gym.repository.ExerciseRepository;
import br.com.gym.repository.WorkoutRepository;

@Service
public class WorkoutService {

	@Autowired
    private WorkoutRepository workoutRepository;
	
	
	@Autowired
    private ExerciseRepository exerciseRepository;
    
	public List<Workout> getWorkoutsByCategory(String category) {
        return workoutRepository.findByCategory(category);
    }
	
	public List<Workout> searchWorkoutsByName(String name) {
        return workoutRepository.findByNameContainingIgnoreCase(name);
    }
	
    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = workoutRepository.findAll();
        workouts.forEach(workout -> workout.getExercises());
        return workouts;
    }
    
    public Workout createWorkout(Workout workout) {
        if (isDuplicateWorkout(workout)) {
            throw new IllegalArgumentException("Treino duplicado. Não é possível criar este treino.");
        }
        
        Workout savedWorkout = workoutRepository.save(workout);

        if (workout.getExercises() != null && !workout.getExercises().isEmpty()) {
            for (Exercise exercise : workout.getExercises()) {
                exercise.setWorkout(savedWorkout);
                exerciseRepository.save(exercise);
            }
        }
        return savedWorkout;
    }
    
    private boolean isDuplicateWorkout(Workout workout) {
        List<Workout> existingWorkouts = workoutRepository.findByName(workout.getName());

        for (Workout existingWorkout : existingWorkouts) {
            if (areExercisesEqual(existingWorkout.getExercises(), workout.getExercises())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean areExercisesEqual(List<Exercise> exercises1, List<Exercise> exercises2) {
        if (exercises1 == null || exercises2 == null || exercises1.size() != exercises2.size()) {
            return false;
        }
        for (int i = 0; i < exercises1.size(); i++) {
            Exercise ex1 = exercises1.get(i);
            Exercise ex2 = exercises2.get(i);
            if (!ex1.getName().equals(ex2.getName()) || !ex1.getDescription().equals(ex2.getDescription())) {
                return false;
            }
        }
        return true; 
    }
    
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id).orElse(null); 
    }

}
