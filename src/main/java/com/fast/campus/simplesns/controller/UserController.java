package com.fast.campus.simplesns.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fast.campus.simplesns.controller.request.UserLoginRequest;
import com.fast.campus.simplesns.controller.response.AlarmResponse;
import com.fast.campus.simplesns.controller.response.Response;
import com.fast.campus.simplesns.controller.response.UserJoinResponse;
import com.fast.campus.simplesns.controller.request.UserJoinRequest;
import com.fast.campus.simplesns.controller.response.UserLoginResponse;
import com.fast.campus.simplesns.model.UserDto;
import com.fast.campus.simplesns.service.AlarmService;
import com.fast.campus.simplesns.service.UserService;
import com.fast.campus.simplesns.utils.ClassUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final AlarmService alarmService;

	@PostMapping("/join")
	public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
		return Response.success(
			UserJoinResponse.fromUserDto(userService.join(request.getName(), request.getPassword())));
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

	@GetMapping("/alarm")
	public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
		UserDto user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), UserDto.class);
		return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
	}

	@GetMapping(value = "/alarm/subscribe")
	public SseEmitter subscribe(Authentication authentication) {
		log.info("subscribe");
		UserDto user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), UserDto.class);
		return alarmService.connectNotification(user.getId());
	}
}
