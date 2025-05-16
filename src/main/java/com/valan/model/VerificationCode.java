package com.valan.model;

import com.valan.domain.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class VerificationCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String otp;
	
	@OneToOne
	private User user;
	
	private String eamil;
	
	private String mobile;
	
	private VerificationType verificationType;
	
	
}
