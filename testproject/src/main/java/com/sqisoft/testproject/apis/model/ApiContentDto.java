package com.sqisoft.testproject.apis.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.sqisoft.testproject.domain.ContentEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiContentDto
{

	@Getter
	@Setter
	public static class find
	{
		@NotEmpty
		private Integer contentSeq;
	}

	@Getter
	@Setter
	public static class save
	{
		private String contentName;

		@NotEmpty
		private Integer categorySeq;

		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class update
	{
		@NotEmpty
		private Integer contentSeq;

		private String contentName;

		private Integer categorySeq;

		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class delete
	{
		@NotEmpty
		private Integer contentSeq;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class info
	{
		private Integer contentSeq;

		private String contentName;

		private Integer categorySeq;

		private ApiContentFileDto.info apiContentFileDto;

		public info(ContentEntity contentEntity)
		{
			contentSeq = contentEntity.getContentSeq();
			contentName = contentEntity.getContentName();
			categorySeq = contentEntity.getCategoryEntity().getCategorySeq();
			apiContentFileDto = new ApiContentFileDto.info(contentEntity.getContentFileEntity());
		}
	}

}
