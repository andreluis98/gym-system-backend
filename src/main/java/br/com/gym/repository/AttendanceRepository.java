package br.com.gym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gym.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	 Optional<Attendance> findTopByUserIdAndWorkoutIdOrderByDateDesc(Long userId, Long workoutId);
}
