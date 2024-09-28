package com.fast.campus.simplesns.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fast.campus.simplesns.exception.ErrorCode;
import com.fast.campus.simplesns.exception.SimpleSnsApplicationException;
import com.fast.campus.simplesns.fixture.UserEntityFixture;
import com.fast.campus.simplesns.model.entity.UserEntity;
import com.fast.campus.simplesns.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;

	@MockBean
	UserRepository userRepository;

	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	void 로그인_정상작동() {
		String username = "test";
		String password = "password";
		UserEntity user = UserEntityFixture.get(username, password);

		when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
		when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);

		Assertions.assertDoesNotThrow(() -> userService.login(username, password));
	}

	@Test
	void 로그인시_유저가_존재하지_않으면_에러를_내뱉는다() {
		String userName = "name";
		String password = "password";

		when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());


		SimpleSnsApplicationException exception = Assertions.assertThrows(SimpleSnsApplicationException.class
			, () -> userService.login(userName, password));

		Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
	}


	@Test
	void 로그인시_패스워드가_다르면_에러를_내뱉는다() {
		String userName = "name";
		String password = "password";
		String wrongPassword = "password1";

		when(userRepository.findByUserName(userName)).thenReturn(Optional.of(UserEntityFixture.get(userName, wrongPassword)));
		when(bCryptPasswordEncoder.matches(password, wrongPassword)).thenReturn(false);

		SimpleSnsApplicationException exception = Assertions.assertThrows(SimpleSnsApplicationException.class
			, () -> userService.login(userName, password));

		Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, exception.getErrorCode());
	}

	@Test
	void 회원가입이_정상동작한다() {
		String userName = "name";
		String password = "password";

		when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
		when(bCryptPasswordEncoder.encode(password)).thenReturn("password_encrypt");
		when(userRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, "password_encrypt")));

		Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
	}


	@Test
	void 회원가입시_아이디가_중복되면_다르면_에러를_내뱉는다() {
		String userName = "name";
		String password = "password";

		when(userRepository.findByUserName(userName)).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

		SimpleSnsApplicationException exception = Assertions.assertThrows(SimpleSnsApplicationException.class,
			() -> userService.join(userName, password));

		Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
	}
}
