package br.com.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.model.Exercise;
import br.com.gym.model.Workout;
import br.com.gym.repository.ExerciseRepository;

@Service
public class ExerciseService {

	@Autowired
    private ExerciseRepository exerciseRepository;
	
    public Exercise createExercise(Exercise exercise, Long workoutId) {
        Workout workout = new Workout();
        workout.setId(workoutId); 
        exercise.setWorkout(workout); 

        return exerciseRepository.save(exercise);
    }
}
