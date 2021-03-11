package com.sqisoft.testproject.apis.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sqisoft.testproject.domain.CategoryEntity;

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
		private Integer categorySeq;
	}

	@Getter
	@Setter
	public static class categorySave
	{
		private String categoryName;

		@NotEmpty
		private Integer museumSeq;

		@NotEmpty
		private Integer deviceSeq;
	}

	@Getter
	@Setter
	public static class categoryUpdate
	{
		@NotEmpty
		private Integer categorySeq;

		private String categoryName;

		private Integer museumSeq;

		private Integer deviceSeq;
	}

	@Getter
	@Setter
	public static class categoryDelete
	{
		@NotEmpty
		private Integer categorySeq;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class categoryInfo
	{
		private Integer categorySeq;

		private String categoryName;

		private Integer museumSeq;

		private Integer deviceSeq;

		private List<ApiContentDto.contentInfo> apiContentDto = new ArrayList<ApiContentDto.contentInfo>();

		public categoryInfo(CategoryEntity categoryEntity)
		{
			categorySeq = categoryEntity.getCategorySeq();
			categoryName = categoryEntity.getCategoryName();
			museumSeq = categoryEntity.getMuseumEntity().getMuseumSeq();
			deviceSeq = categoryEntity.getDeviceEntity().getDeviceSeq();

			for (int i = 0; i < categoryEntity.getContentEntity().size(); i++)
			{
				ApiContentDto.contentInfo contentdata = new ApiContentDto.contentInfo(categoryEntity.getContentEntity().get(i));
				apiContentDto.add(contentdata);
			}

		}
	}

}
