package com.sqisoft.testproject.apis.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiMuseumDto
{
	@Getter
	@Setter
	public static class find
	{
		@NotEmpty
		private Integer museumSeq;
	}

	@Getter
	@Setter
	public static class save
	{
		private String museumName;

		private String museumTel;

		private String museumLoc;

		@NotEmpty
		private Integer[] deviceSeq;

		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class update
	{
		@NotEmpty
		private Integer museumSeq;

		private String museumName;

		private String museumTel;

		private String museumLoc;

		private Integer[] deviceSeq;

		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class delete
	{
		@NotEmpty
		private Integer museumSeq;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class info
	{
		private Integer museumSeq;

		private String museumName;

		private String museumTel;

		private String museumLoc;

		private ApiContentFileDto.info apiContentFileDto;

		private List<ApiDeviceDto.info> apiDeviceDto = new ArrayList<ApiDeviceDto.info>();

		private List<ApiCategoryDto.info> apiCategoryDto = new ArrayList<ApiCategoryDto.info>();

		public info(MuseumEntity museumEntity)
		{
			museumSeq = museumEntity.getMuseumSeq();
			museumName = museumEntity.getMuseumName();
			museumTel = museumEntity.getMuseumTel();
			museumLoc = museumEntity.getMuseumLoc();
			apiContentFileDto = new ApiContentFileDto.info(museumEntity.getContentFileEntity());
			for (int i = 0; i < museumEntity.getDeviceEntity().size(); i++)
			{
				apiDeviceDto.add(new ApiDeviceDto.info(museumEntity.getDeviceEntity().get(i)));
			}
			for (int i = 0; i < museumEntity.getCategoryEntity().size(); i++)
			{
				apiCategoryDto.add(new ApiCategoryDto.info(museumEntity.getCategoryEntity().get(i)));
			}

		}
	}
}
