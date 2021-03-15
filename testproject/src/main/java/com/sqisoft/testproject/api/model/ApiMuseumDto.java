package com.sqisoft.testproject.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sqisoft.testproject.domain.MuseumEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "박물관")
public class ApiMuseumDto
{
	@Getter
	@Setter
	public static class museumFind
	{
		@NotEmpty
		@Schema(description = "박물관_아이디", nullable = false)
		private Integer museumSeq;

		public museumFind(Integer museumSeqs)
		{
			this.museumSeq = museumSeqs;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class museumInfo
	{
		@Schema(description = "박물관_아이디")
		private Integer museumSeq;

		@Schema(description = "박물관_이름")
		private String museumName;

		@Schema(description = "박물관_전화번호")
		private String museumTel;

		@Schema(description = "박물관_위치")
		private String museumLoc;

		@Schema(description = "로고파일_정보")
		private ApiContentFileDto.contentFileLogo apiContentFileDto;

		@Schema(description = "설치된_디바이스_정보")
		private List<ApiDeviceDto.deviceInfo> apiDeviceDto = new ArrayList<ApiDeviceDto.deviceInfo>();

		@Schema(description = "등록된_카테고리_정보")
		private List<ApiCategoryDto.categoryInfo> apiCategoryDto = new ArrayList<ApiCategoryDto.categoryInfo>();

		public museumInfo(MuseumEntity museumEntity)
		{
			museumSeq = museumEntity.getMuseumSeq();
			museumName = museumEntity.getMuseumName();
			museumTel = museumEntity.getMuseumTel();
			museumLoc = museumEntity.getMuseumLoc();
			apiContentFileDto = new ApiContentFileDto.contentFileLogo(museumEntity.getContentFileEntity());
			for (int i = 0; i < museumEntity.getDeviceEntity().size(); i++)
			{
				apiDeviceDto.add(new ApiDeviceDto.deviceInfo(museumEntity.getDeviceEntity().get(i)));
			}
			for (int i = 0; i < museumEntity.getCategoryEntity().size(); i++)
			{
				apiCategoryDto.add(new ApiCategoryDto.categoryInfo(museumEntity.getCategoryEntity().get(i)));
			}
		}
	}
}
