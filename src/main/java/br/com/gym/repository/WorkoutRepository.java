package br.com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gym.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

	List<Workout> findByCategory(String category);
	
	List<Workout> findByNameContainingIgnoreCase(String Name);
	
    List<Workout> findByName(String name);
	
}
