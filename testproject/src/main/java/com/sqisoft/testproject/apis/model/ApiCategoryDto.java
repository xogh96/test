package com.sqisoft.testproject.apis.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sqisoft.testproject.domain.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiCategoryDto
{
	@Getter
	@Setter
	public static class find
	{
		private Integer categorySeq;
	}
	@Getter
	@Setter
	public static class save
	{
		private Integer categorySeq;

		private String categoryName;

		private Integer museumSeq;

		private Integer deviceSeq;
	}
	
	@Getter
	@Setter
	public static class delete
	{
		private Integer categorySeq;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class info
	{
		private Integer categorySeq;

		private String categoryName;

		private Integer museumSeq;

		private Integer deviceSeq;

		private List<ApiContentDto.info> apiContentDto = new ArrayList<ApiContentDto.info>();

		public info(CategoryEntity categoryEntity)
		{
			categorySeq = categoryEntity.getCategorySeq();
			categoryName = categoryEntity.getCategoryName();
			museumSeq = categoryEntity.getMuseumEntity().getMuseumSeq();
			deviceSeq = categoryEntity.getDeviceEntity().getDeviceSeq();

			for (int i = 0; i < categoryEntity.getContentEntity().size(); i++)
			{
				ApiContentDto.info contentdata = new ApiContentDto.info(categoryEntity.getContentEntity().get(i));
				apiContentDto.add(contentdata);
			}

		}
	}

}
