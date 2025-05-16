package com.valan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valan.domain.VerificationType;
import com.valan.model.User;
import com.valan.model.VerificationCode;
import com.valan.repository.VerificationCodeRepo;
import com.valan.utils.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService  {
	
	@Autowired
	private VerificationCodeRepo verificationCodeRepo;

	@Override
	public VerificationCode sendVerificationCode(User user,VerificationType verificationType) {
		VerificationCode verificationCode =new VerificationCode();
		verificationCode.setOtp(OtpUtils.genarateOTP());
		verificationCode.setVerificationType(verificationType);
		verificationCode.setUser(user);
		return verificationCodeRepo.save(verificationCode);
	}

	@Override
	public VerificationCode getVerificationCodeById(Long id) throws Exception {
		
		Optional<VerificationCode> verificationCode=verificationCodeRepo.findById(id);
		
		if(verificationCode.isPresent()) {
			return verificationCode.get();
		}
		 
		throw new Exception("Nerification code not fount");
	}

	@Override
	public VerificationCode getVerificationCodeByUser(Long userId) {
		return verificationCodeRepo.findByUserId(userId);
	}

	@Override
	public void deleteVerificationCode(VerificationCode verificationCode) {
		verificationCodeRepo.delete(verificationCode);
	}

}
