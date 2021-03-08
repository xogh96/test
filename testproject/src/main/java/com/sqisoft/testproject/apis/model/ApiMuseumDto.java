package com.sqisoft.testproject.apis.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

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
		private Integer museumSeq;
	}

	@Getter
	@Setter
	public static class save
	{
		private Integer museumSeq;
		private String museumName;
		private String museumTel;
		private String museumLoc;
		private Integer fileSeq;
		private String fileName;
		private String filePhyName;
		private String fileThumbPhyName;
		private Long fileSize;
		private String fileContentType;
		private Integer fileOrder;
		private Integer [] deviceSeq;
	}

	@Getter
	@Setter
	public static class delete
	{
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
