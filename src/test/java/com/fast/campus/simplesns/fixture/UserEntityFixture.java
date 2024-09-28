package com.fast.campus.simplesns.fixture;

import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.UserRole;
import com.fast.campus.simplesns.model.entity.UserEntity;

public class UserEntityFixture {
	public static UserEntity get(String username, String password) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setUserName(username);
		userEntity.setPassword(password);
		userEntity.setRole(UserRole.USER);
		userEntity.setRegisteredAt(LocalDateTime.now());
		return userEntity;
	}
}
