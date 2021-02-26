package com.sqisoft.testproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.domain.UserEntity;
import com.sqisoft.testproject.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService
{

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		UserEntity user = userRepository.findByUserId(username);

		if (user == null)
		{
			log.warn(String.format("User name not found by %s", username));
			throw new UsernameNotFoundException("User name not found");
		}

		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		if ("admin".equals(username))
		{
			auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else
		{
			auth.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new User(user.getUserId(), user.getUserPassword(), auth);
	}

	/** 회원가입을 구현할 때 사용하는 메소드 **/
	@Transactional
	public UserEntity signUpUser(UserEntity userEntity)
	{
		userEntity.setUserPassword(passwordEncoder.encode(userEntity.getUserPassword()));
		return userRepository.save(userEntity);
	}

}
