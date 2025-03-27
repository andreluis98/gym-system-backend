package br.com.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.gym.model.Attendance;
import br.com.gym.model.User;
import br.com.gym.model.Workout;
import br.com.gym.repository.AttendanceRepository;
import br.com.gym.repository.UserRepository;
import br.com.gym.repository.WorkoutRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkoutRepository workoutRepository;
    
    public Attendance markAttendance(Long userId, Long workoutId, boolean isPresent) {
        Attendance attendance = new Attendance();
        
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        user.setId(userId);

        workout.setId(workoutId);

        attendance.setUser(user);
        attendance.setWorkout(workout);
        attendance.setPresent(isPresent);
        attendance.setDate(LocalDate.now()); 

        return attendanceRepository.save(attendance);
    }
    
    public Optional<Attendance> getAttendance(Long userId, Long workoutId) {
        return attendanceRepository.findTopByUserIdAndWorkoutIdOrderByDateDesc(userId, workoutId);
    }

}
