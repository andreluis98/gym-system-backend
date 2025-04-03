package br.com.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gym.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
