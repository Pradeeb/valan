package com.valan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valan.model.VerificationCode;

@Repository
public interface VerificationCodeRepo extends JpaRepository<VerificationCode,Long> {

	public VerificationCode findByUserId(Long id);
}
