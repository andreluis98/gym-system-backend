package br.com.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gym.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
