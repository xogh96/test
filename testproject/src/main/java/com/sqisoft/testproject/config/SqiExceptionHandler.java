package com.sqisoft.testproject.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.sqisoft.testproject.model.ErrorMessage;


@ControllerAdvice(basePackages = { "com.sqisoft.testproject.controller" })
public class SqiExceptionHandler
{
	@ExceptionHandler(value = { SqiException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorMessage sqiExceptionHandler(RuntimeException ex, WebRequest request)
	{
		ErrorMessage em = new ErrorMessage();
		em.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		em.setMessage(ex.getMessage());
		return em;
	}
}