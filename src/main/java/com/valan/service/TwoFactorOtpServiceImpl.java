package com.valan.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valan.model.TwoFactorOTP;
import com.valan.model.User;
import com.valan.repository.TwoFactorOtpRepository;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService{
	
	@Autowired private TwoFactorOtpRepository twoFactorOtpRepository;

	@Override
	public TwoFactorOTP createTwoFactorOtp(User user, String otp, String JWT) {
		
		UUID uuid=UUID.randomUUID();
		
		String id=uuid.toString();
		
		TwoFactorOTP twoFactorOtp=new TwoFactorOTP();
		
		twoFactorOtp.setOtp(otp);
		twoFactorOtp.setJwt(JWT);
		twoFactorOtp.setId(id);
		twoFactorOtp.setUser(user);
		
		return twoFactorOtpRepository.save(twoFactorOtp);
	}

	@Override
	public TwoFactorOTP findByUser(Long userId) {
		// TODO Auto-generated method stub
		return twoFactorOtpRepository.findByUserId(userId);
	}

	@Override
	public TwoFactorOTP findByID(String id) {
		Optional<TwoFactorOTP> otp = twoFactorOtpRepository.findById(id);
		return otp.orElse(null);
	}

	@Override
	public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
		// TODO Auto-generated method stub
		return twoFactorOtp.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOTP(TwoFactorOTP twoFactorOtp) {
		twoFactorOtpRepository.delete(twoFactorOtp);
	}

}
