package com.fast.campus.simplesns.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
	;

	private final HttpStatus status;
	private final String message;
}
