package com.sqisoft.testproject.api.model;

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
	public static class contentFind
	{
		@NotEmpty
		private Integer contentSeq;
	}

	@Getter
	@Setter
	public static class contentSave
	{
		private String contentName;

		@NotEmpty
		private Integer categorySeq;

		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class contentUpdate
	{
		@NotEmpty
		private Integer contentSeq;

		private String contentName;

		private Integer categorySeq;

		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class contentDelete
	{
		@NotEmpty
		private Integer contentSeq;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class contentInfo
	{
		private Integer contentSeq;

		private String contentName;

		private Integer categorySeq;

		private ApiContentFileDto.contentFileInfo apiContentFileDto;

		public contentInfo(ContentEntity contentEntity)
		{
			contentSeq = contentEntity.getContentSeq();
			contentName = contentEntity.getContentName();
			categorySeq = contentEntity.getCategoryEntity().getCategorySeq();
			apiContentFileDto = new ApiContentFileDto.contentFileInfo(contentEntity.getContentFileEntity());
		}
	}

}
