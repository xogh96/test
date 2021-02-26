package com.sqisoft.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sqisoft.testproject.service.UserService;

@Component
public class AuthProvider implements AuthenticationProvider
{

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails user = userService.loadUserByUsername(username);
		if (!passwordEncoder.matches(password, user.getPassword()))
		{
			throw new BadCredentialsException("정보가 일치하지 않습니다");
		}
		return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
