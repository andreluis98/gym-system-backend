package br.com.gym.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @Column(nullable = false)
	    private Double amount;

	    @Column(nullable = false)
	    private Date paymentDate;

	    @Column(nullable = false)
	    private Boolean isPaid;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public Date getPaymentDate() {
			return paymentDate;
		}

		public void setPaymentDate(Date paymentDate) {
			this.paymentDate = paymentDate;
		}

		public Boolean getIsPaid() {
			return isPaid;
		}

		public void setIsPaid(Boolean isPaid) {
			this.isPaid = isPaid;
		}

		@Override
		public String toString() {
			return "Payment [id=" + id + ", user=" + user + ", amount=" + amount + ", paymentDate=" + paymentDate
					+ ", isPaid=" + isPaid + "]";
		}
	    
	    

}
