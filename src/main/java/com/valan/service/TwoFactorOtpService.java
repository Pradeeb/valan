package com.valan.service;

import com.valan.model.TwoFactorOTP;
import com.valan.model.User;

public interface TwoFactorOtpService {
	
	TwoFactorOTP createTwoFactorOtp(User user,String otp,String JWT);
	
	TwoFactorOTP findByUser(Long userId);
	
	TwoFactorOTP findByID(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp,String otp);
	
	void deleteTwoFactorOTP(TwoFactorOTP twoFactorOtp);

}
