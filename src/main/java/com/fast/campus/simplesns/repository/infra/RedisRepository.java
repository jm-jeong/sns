package com.fast.campus.simplesns.repository.infra;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fast.campus.simplesns.model.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepository {

	private final RedisTemplate<String, UserDto> userRedisTemplate;

	private final static Duration USER_CACEH_TTL = Duration.ofDays(3);

	public void setUser(UserDto userDto) {
		String key = getKey(userDto.getUsername());
		log.info("Set User to Redis {}({})", key, userDto);
		userRedisTemplate.opsForValue().set(key, userDto, USER_CACEH_TTL);
	}

	public Optional<UserDto> getUser(String userName) {
		UserDto data = userRedisTemplate.opsForValue().get(getKey(userName));
		log.info("Get User from Redis {}", data);
		return Optional.ofNullable(data);
	}

	private String getKey(String userName) {
		return "UID:" + userName;
	}
}
