package com.valan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valan.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByEmail(String mail);

}
