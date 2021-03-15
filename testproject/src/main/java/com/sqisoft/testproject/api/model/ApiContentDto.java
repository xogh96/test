package com.sqisoft.testproject.api.model;

import javax.validation.constraints.NotEmpty;

import com.sqisoft.testproject.domain.ContentEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiContentDto
{

	@Getter
	@Setter
	public static class contentFind
	{
		@Schema(description = "콘텐츠_아이디", nullable = false)
		@NotEmpty
		private Integer contentSeq;
		
		public contentFind(Integer conseq)
		{
			this.contentSeq = conseq;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class contentInfo
	{
		@Schema(description = "콘텐츠_아이디")
		private Integer contentSeq;

		@Schema(description = "콘텐츠_이름")
		private String contentName;

		@Schema(description = "카테고리_아이디")
		private Integer categorySeq;

		@Schema(description = "콘텐츠_파일" )
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
