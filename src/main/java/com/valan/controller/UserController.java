package com.valan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.valan.domain.VerificationType;
import com.valan.model.User;
import com.valan.model.VerificationCode;
import com.valan.service.EmailService;
import com.valan.service.IUserService;
import com.valan.service.IVerificationCodeService;

@RestController
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private IVerificationCodeService verificationCodeService;
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User> gerUserProfile(@RequestHeader("Autherization") String jwt) throws Exception{
			User user =userService.findUserProfileByJet(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@PostMapping("/api/users/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Autherization") String jwt,
			@PathVariable VerificationType verificationType) throws Exception{
		User user =userService.findUserProfileByJet(jwt);
		
		VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
		
		if(verificationCode == null) {
			verificationCodeService.sendVerificationCode(user, verificationType);
		}
		if(verificationType.equals(VerificationType.EMAIL)) {
          emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
		}
		
	  return new ResponseEntity<String>("Verification OTP send successfully",HttpStatus.OK);
			
	}
	
	@PatchMapping("/api/users/enable-two-facto/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Autherization") String jwt,@PathVariable String otp) throws Exception{
		User user =userService.findUserProfileByJet(jwt);

		VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
		
		String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEamil():verificationCode.getMobile();
		
		boolean isVerified=verificationCode.getOtp().equals(otp);
		
		if(isVerified) {
			User UpdatedUser=userService.enableTwoFactorAuthendication(verificationCode.getVerificationType(), sendTo, user);
			verificationCodeService.deleteVerificationCode(verificationCode);
			return new ResponseEntity<User>(UpdatedUser,HttpStatus.OK);
		}
		
		throw new Exception("Wrong OTP");
		
	}
	
	
}
