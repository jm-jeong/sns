package com.fast.campus.simplesns.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fast.campus.simplesns.controller.request.UserJoinRequest;
import com.fast.campus.simplesns.controller.request.UserLoginRequest;
import com.fast.campus.simplesns.exception.ErrorCode;
import com.fast.campus.simplesns.exception.SimpleSnsApplicationException;
import com.fast.campus.simplesns.model.UserDto;
import com.fast.campus.simplesns.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithAnonymousUser
	public void 회원가입() throws Exception {
		String username = "testName";
		String password = "testPassword";

		when(userService.join(username, password)).thenReturn(mock(UserDto.class));

		mockMvc.perform(post("/api/v1/users/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserJoinRequest("testName", "testPassword")))
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void 회원가입시_같은아이디로_가입하면_에러발생() throws Exception {
		String username = "testName";
		String password = "testPassword";
		when(userService.join(username, password)).thenThrow(
			new SimpleSnsApplicationException(ErrorCode.DUPLICATE_USERNAME));

		mockMvc.perform(post("/api/v1/users/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserJoinRequest("testName", "testPassword")))
			)
			.andDo(print())
			.andExpect(status().is(ErrorCode.DUPLICATE_USERNAME.getStatus().value()));
	}

	@Test
	@WithAnonymousUser
	public void 로그인() throws Exception {
		String username = "testName";
		String password = "testPassword";
		when(userService.login(username, password)).thenReturn("testToken");

		mockMvc.perform(post("/api/v1/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest("testName", "testPassword")))
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void 로그인시_비밀번호가_다르면_에러발생() throws Exception {
		String username = "testName";
		String password = "testPassword";

		when(userService.login(username, password)).thenThrow(new SimpleSnsApplicationException(ErrorCode.INVALID_PASSWORD));

		mockMvc.perform(post("/api/v1/users/login")
		.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(new UserLoginRequest("testName", "testPassword")))
		).andDo(print())
			.andExpect(status().is(ErrorCode.INVALID_PASSWORD.getStatus().value()));
	}
}
