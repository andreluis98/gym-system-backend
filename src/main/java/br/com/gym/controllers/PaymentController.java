package br.com.gym.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gym.dto.PaymentRequest;
import br.com.gym.model.Payment;
import br.com.gym.model.User;
import br.com.gym.repository.PaymentRepository;
import br.com.gym.repository.UserRepository;
import br.com.gym.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/gym/payments")
@Tag(name = "Payments", description = "Endpoints para gerenciamento de pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(
            summary = "Criar um novo pagamento",
            description = "Registra um novo pagamento associado a um usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro ao registrar pagamento"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPayment(@RequestBody PaymentRequest paymentRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
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
            if (paymentRequest.getPaymentDate() != null) {
                payment.setPaymentDate(new java.sql.Date(paymentRequest.getPaymentDate().getTime()));
            }
            payment.setUser(user);

            paymentRepository.save(payment);

            response.put("status", "success");
            response.put("message", "Pagamento registrado com sucesso!");
            response.put("payment", payment);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao registrar pagamento: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Operation(
            summary = "Obter pagamento por ID",
            description = "Recupera o pagamento associado ao ID informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPaymentById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment != null) {
                response.put("status", "success");
                response.put("payment", payment);
                return ResponseEntity.status(200).body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Pagamento não encontrado");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao consultar pagamento: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Operation(
            summary = "Verificar status de pagamento",
            description = "Verifica se o pagamento foi realizado para um aluno",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status de pagamento encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @GetMapping("/check/{userId}")
    public ResponseEntity<Map<String, Object>> checkPaymentStatus(@PathVariable Long userId) {
    	System.out.println(userId);
    	Map<String, Object> response = new HashMap<>();
        try {
            Boolean isPaid = paymentService.checkIfPaid(userId);
            response.put("status", "success");
            response.put("isPaid", isPaid);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erro ao verificar status de pagamento: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    
}
