package com.fast.campus.simplesns.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fast.campus.simplesns.controller.response.Response;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(SimpleSnsApplicationException.class)
	public ResponseEntity<?> errorHandler(SimpleSnsApplicationException e) {
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(Response.error(e.getErrorCode().name(), e.getMessage()));
	}
}
