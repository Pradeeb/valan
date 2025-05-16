package com.valan.service;

import com.valan.domain.VerificationType;
import com.valan.model.User;

public interface IUserService {
	
	public User findUserProfileByJet(String jwt) throws Exception;
	public User findUserByEmail(String email) throws Exception;
	public User findUserById(long userId) throws Exception;
	public User enableTwoFactorAuthendication(VerificationType verificationType,String sendTo,User user);
	User updatePassword(User user,String newPassword);
	

}
