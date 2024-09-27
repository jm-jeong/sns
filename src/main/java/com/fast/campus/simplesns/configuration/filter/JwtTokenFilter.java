package com.fast.campus.simplesns.configuration.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fast.campus.simplesns.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
	private final UserService userService;
	private final String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain) throws ServletException, IOException {

		final String header = request.getHeader("Bearer");
		if (header == null || !header.startsWith("Bearer ")) {
			log.warn("header is null or Authorization Header does not start with Bearer");
			chain.doFilter(request, response);
			return;
		}

		final String token = header.replace("Bearer ", "");


	}

}
