package com.fast.campus.simplesns.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fast.campus.simplesns.controller.request.UserLoginRequest;
import com.fast.campus.simplesns.controller.response.Response;
import com.fast.campus.simplesns.controller.response.UserJoinResponse;
import com.fast.campus.simplesns.controller.request.UserJoinRequest;
import com.fast.campus.simplesns.controller.response.UserLoginResponse;
import com.fast.campus.simplesns.model.UserDto;
import com.fast.campus.simplesns.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
		UserDto userDto = userService.join(userJoinRequest.getName(), userJoinRequest.getPassword());
		return Response.success(UserJoinResponse.fromUserDto(userDto));
	}

	@PostMapping("/login")
	public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return Response.success(
			new UserLoginResponse(userService.login(userLoginRequest.getName(), userLoginRequest.getPassword())));
	}

	@GetMapping("/me")
	public Response<UserJoinResponse> me(Authentication authentication) {
		return Response.success(UserJoinResponse.fromUserDto(userService.loadUserByUsername(authentication.getName())));
	}
}
