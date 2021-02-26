package com.sqisoft.testproject.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SqiException extends RuntimeException
{
	private static final long serialVersionUID = 8534774128017912448L;

	private String message;

	public SqiException(String message)
	{
		this.message = message;
	}
}
