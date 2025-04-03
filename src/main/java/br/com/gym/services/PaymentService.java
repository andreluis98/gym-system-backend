package br.com.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gym.dto.PaymentRequest;
import br.com.gym.model.Payment;
import br.com.gym.model.User;
import br.com.gym.repository.PaymentRepository;
import br.com.gym.repository.UserRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment createPayment(PaymentRequest paymentRequest) {
        if (paymentRequest == null || paymentRequest.getUsername() == null) {
            throw new RuntimeException("O username não foi informado no pagamento.");
        }

        User user = userRepository.findByUsername(paymentRequest.getUsername());

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado com o username: " + paymentRequest.getUsername());
        }

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setIsPaid(false); 
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setUser(user);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Boolean checkIfPaid(Long studentId) {
        return paymentRepository.findAll().stream()
                .anyMatch(payment -> payment.getUser().getId().equals(studentId) && payment.getIsPaid());
    }
}
