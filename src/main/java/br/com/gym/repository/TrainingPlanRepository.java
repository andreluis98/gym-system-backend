package br.com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gym.model.TrainingPlan;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

	List<TrainingPlan> findByUserId(Long userId);

}
