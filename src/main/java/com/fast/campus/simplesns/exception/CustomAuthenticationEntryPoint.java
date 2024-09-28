package com.fast.campus.simplesns.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fast.campus.simplesns.controller.response.Response;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(ErrorCode.INVALID_TOKEN.getStatus().value());
		response.getWriter().write(Response.error(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage()).toStream());
	}
}
