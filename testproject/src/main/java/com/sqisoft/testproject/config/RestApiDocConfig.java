package com.sqisoft.testproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Component
public class RestApiDocConfig
{
	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("api doc").description("test프로젝트 api doc 입니다");
		return new OpenAPI().components(new Components()).info(info);
	}
}
