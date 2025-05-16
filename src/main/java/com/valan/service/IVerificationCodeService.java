package com.valan.service;

import com.valan.domain.VerificationType;
import com.valan.model.User;
import com.valan.model.VerificationCode;

public interface IVerificationCodeService {
	
	VerificationCode sendVerificationCode(User user,VerificationType verificationCode);
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	VerificationCode getVerificationCodeByUser(Long userId);
	void deleteVerificationCode(VerificationCode verificationCode);


}
