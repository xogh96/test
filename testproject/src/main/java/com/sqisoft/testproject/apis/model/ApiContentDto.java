package com.sqisoft.testproject.apis.model;

import com.sqisoft.testproject.domain.CategoryEntity;
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
		private Integer contentSeq;
	}

	@Getter
	@Setter
	public static class save
	{
		private Integer contentSeq;

		private String contentName;

		private Integer categorySeq;

		private Integer fileSeq;

		private String fileName;

		private String filePhyName;

		private String fileThumbPhyName;

		private Long fileSize;

		private String fileContentType;

		private Integer fileOrder;
	}

	@Getter
	@Setter
	public static class delete
	{
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
