package com.fast.campus.simplesns.controller.response;

import com.fast.campus.simplesns.model.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
	private Integer id;
	private String name;

	public static UserResponse fromUser(UserDto user) {
		return new UserResponse(
			user.getId(),
			user.getUsername()
		);
	}
}
