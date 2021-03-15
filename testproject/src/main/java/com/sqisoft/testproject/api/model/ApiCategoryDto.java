package com.sqisoft.testproject.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sqisoft.testproject.domain.CategoryEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiCategoryDto
{
	@Getter
	@Setter
	public static class categoryFind
	{
		@NotEmpty
		@Schema(description = "카테고리_아이디", nullable = false)
		private Integer categorySeq;
		
		public categoryFind(Integer categorySeqs)
		{
			this.categorySeq = categorySeqs;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class categoryInfo
	{
		@Schema(description = "카테고리_아이디")
		private Integer categorySeq;

		@Schema(description = "카테고리_이름")
		private String categoryName;

		@Schema(description = "박물관_아이디")
		private Integer museumSeq;

		@Schema(description = "콘텐츠_리스트")
		private List<ApiContentDto.contentInfo> apiContentDto = new ArrayList<ApiContentDto.contentInfo>();

		public categoryInfo(CategoryEntity categoryEntity)
		{
			categorySeq = categoryEntity.getCategorySeq();
			categoryName = categoryEntity.getCategoryName();
			museumSeq = categoryEntity.getMuseumEntity().getMuseumSeq();

			for (int i = 0; i < categoryEntity.getContentEntity().size(); i++)
			{
				ApiContentDto.contentInfo contentdata = new ApiContentDto.contentInfo(categoryEntity.getContentEntity().get(i));
				apiContentDto.add(contentdata);
			}

		}
	}

}
