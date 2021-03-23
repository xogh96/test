package com.sqisoft.testproject.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AuthFailHandler implements AuthenticationFailureHandler{
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
					throws IOException, ServletException
	{
		if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
			response.sendRedirect("/admin/login/fail?sessionUserId="+request.getParameter("userId"));
		}
	}
	
}
