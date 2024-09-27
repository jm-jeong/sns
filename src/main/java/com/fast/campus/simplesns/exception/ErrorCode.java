package com.fast.campus.simplesns.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
	DUPLICATE_USERNAME(HttpStatus.CONFLICT, "Username already exist"),
	;
	private final HttpStatus status;
	private final String message;
}
