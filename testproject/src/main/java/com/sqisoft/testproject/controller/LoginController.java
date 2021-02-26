package com.sqisoft.testproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqisoft.testproject.domain.UserEntity;
import com.sqisoft.testproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController
{

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String login()
	{
		return "/login/login";
	}

}
