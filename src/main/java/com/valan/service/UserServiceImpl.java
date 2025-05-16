package com.valan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.valan.configurations.JwtProvider;
import com.valan.domain.VerificationType;
import com.valan.model.TwoFactorAuth;
import com.valan.model.User;
import com.valan.repository.UserRepository;

public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserProfileByJet(String jwt) throws Exception {

		String email = JwtProvider.getEmailFromToken(jwt);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new Exception("user not found");
		}

		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new Exception("user not found");
		}

		return user;
	}

	@Override
	public User findUserById(long userId) throws Exception {
		
		Optional<User> user=userRepository.findById(userId);
		if(user == null) {
			throw new Exception("User not found");
		}
		return user.get();
	}

	@Override
	public User enableTwoFactorAuthendication(VerificationType verificationType,String sendTo,User user) {

		TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
		twoFactorAuth.setEnable(true);
		twoFactorAuth.setSendTo(verificationType);
		
		user.setTwoFactorAuth(twoFactorAuth);
		return userRepository.save(user);
	}

	@Override
	public User updatePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		return userRepository.save(user);
	}

}
