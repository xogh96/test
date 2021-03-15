package com.sqisoft.testproject.controller;

import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqisoft.testproject.model.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController
{

	private static final String ERROR_PATH = "/error";

	@Override
	public String getErrorPath()
	{
		return ERROR_PATH;
	}

	@GetMapping("/error")
	public String handleError(HttpServletRequest request, Model model)
	{
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		log.debug("httpStatus : " + httpStatus.toString());
		model.addAttribute("code", status.toString());
		model.addAttribute("msg", httpStatus.getReasonPhrase());
		model.addAttribute("timestamp", new Date());
		return "error/error";
	}

	@PostMapping("/error")
	@ResponseBody
	public ErrorMessage handleError(HttpServletRequest request)
	{
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		ErrorMessage e = null;
		e = new ErrorMessage("요청이 실패했습니다", httpStatus.toString());
		log.debug("httpStatus : " + httpStatus.toString());
		return e;
	}

}