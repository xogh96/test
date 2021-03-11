package com.sqisoft.testproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorMessage
{
	@Schema(description = "에러_메시지")
	private String status;

	@Schema(description = "에러_코드")
	private String message;

	public ErrorMessage(String message, String status)
	{

		this.message = message;

		this.status = status;
	}

	public ErrorMessage()
	{
	}
}
