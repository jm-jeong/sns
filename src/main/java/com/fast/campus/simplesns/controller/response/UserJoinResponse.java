package com.fast.campus.simplesns.controller.response;

import com.fast.campus.simplesns.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
	private Integer id;
	private String name;

	public static UserJoinResponse fromUserDto(UserDto userDto) {
		return new UserJoinResponse(
			userDto.getId(),
			userDto.getPassword()
		);
	}
}
