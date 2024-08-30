package com.itSolution.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itSolution.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer> {

	//select * from user_tbl where uname=:uname
	public UserEntity findByUname(String uname);
}
