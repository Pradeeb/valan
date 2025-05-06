package com.valan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valan.configurations.JwtProvider;
import com.valan.model.TwoFactorOTP;
import com.valan.model.User;
import com.valan.repository.UserRepository;
import com.valan.responses.AuthResponse;
import com.valan.service.CustomUserDetailsService;
import com.valan.service.TwoFactorOtpService;
import com.valan.utils.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private TwoFactorOtpService twoFactorOtpService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

		User isEmailExist = userRepository.findByEmail(user.getEmail().trim());

		if (isEmailExist != null) {
			throw new Exception("Email is already exist!");
		}

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFullName(user.getFullName());
		newUser.setPassword(user.getPassword());

		User savesUser = userRepository.save(newUser);

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(),
				user.getPassword()

		);

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = JwtProvider.genrateToken(auth);

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("Register Success");

		return new ResponseEntity<>(res, HttpStatus.CREATED);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

		String userName = user.getEmail();
		String password = user.getPassword();

		UsernamePasswordAuthenticationToken auth = authenticate(userName, password);
		String jwt = JwtProvider.genrateToken(auth);
		
		if(user.getTwoFactorAuth().isEnable()) {
			AuthResponse res=new AuthResponse();
			res.setMessage("Two factor athentication is enanbel");
			res.setTwoFactorAuthEnabled(true);
			String otp=OtpUtils.genarateOTP();
			
			TwoFactorOTP oldTowFactorOtp=twoFactorOtpService.findByUser(user.getId());
		}

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("Loginsucces Success");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	private UsernamePasswordAuthenticationToken authenticate(String userName, String password) {
		UserDetails	userDeatils=customUserDetailService.loadUserByUsername(userName);
		
         if(userDeatils ==null) {
	         throw  new BadCredentialsException("Invalid user name");
           }
         
         if(!password.equals(userDeatils.getPassword())) {
	         throw  new BadCredentialsException("Invalid user name");
           }
		
		return new UsernamePasswordAuthenticationToken(userDeatils,password,userDeatils.getAuthorities());
	}

}
