package com.fast.campus.simplesns.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fast.campus.simplesns.JwtTokenUtils;
import com.fast.campus.simplesns.exception.ErrorCode;
import com.fast.campus.simplesns.exception.SimpleSnsApplicationException;
import com.fast.campus.simplesns.model.AlarmDto;
import com.fast.campus.simplesns.model.UserDto;
import com.fast.campus.simplesns.model.entity.UserEntity;
import com.fast.campus.simplesns.repository.AlarmEntityRepository;
import com.fast.campus.simplesns.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserEntityRepository userEntityRepository;
	private final AlarmEntityRepository alarmEntityRepository;
	private final BCryptPasswordEncoder encoder;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;

	public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
		return userEntityRepository.findByUserName(username).map(UserDto::fromEntity).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", username))
		);
	}

	public String login(String username, String password) {
		UserDto savedUser = loadUserByUsername(username);
		if (!encoder.matches(password, savedUser.getPassword())) {
			throw new SimpleSnsApplicationException(ErrorCode.INVALID_PASSWORD);
		}
		return JwtTokenUtils.generateAccessToken(username, secretKey, expiredTimeMs);
	}

	@Transactional
	public UserDto join(String username, String password) {
		userEntityRepository.findByUserName(username).ifPresent(
			it -> {
				throw new SimpleSnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,
					String.format("userName is %s", username));
			});

		UserEntity savedUser = userEntityRepository.save(UserEntity.of(username, encoder.encode(password)));
		return UserDto.fromEntity(savedUser);
	}

	@Transactional
	public Page<AlarmDto> alarmList(Integer userId, Pageable pageable) {
		return alarmEntityRepository.findAllByUserId(userId, pageable).map(AlarmDto::fromEntity);
	}
}
