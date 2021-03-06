package com.sqisoft.testproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sqisoft.testproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController
{

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String login(Model model , HttpSession session)
	{
		model.addAttribute("userid", session.getAttribute("sessionUserId"));
		session.invalidate();
		return "login/login";
	}
	
	@GetMapping("/login/fail")
	public String fail(HttpSession session , @RequestParam("sessionUserId") String userid)
	{
		log.debug("도착?");
		session.setAttribute("sessionUserId", userid);
		return "login/loginfail";
	}

}
