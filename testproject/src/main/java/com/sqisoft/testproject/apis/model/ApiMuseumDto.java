package com.sqisoft.testproject.apis.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	@ToString
	public static class museumSave
	{
		@Schema(description = "박물관_이름")
		private String museumName;

		@Schema(description = "박물관_전화번호")
		private String museumTel;

		@Schema(description = "박물관_주소")
		private String museumLoc;

		@NotEmpty
		@Schema(description = "디바이스_아이디", nullable = false)
		private Integer[] deviceSeq;

		@Schema(description = "박물관_로고파일")
		private MultipartFile file;
	}

	@Getter
	@Setter
	@ToString
	public static class museumUpdate
	{
		@NotEmpty
		@Schema(description = "박물관_아이디 (변경할 박물관의 id 입력)", nullable = false, hidden = true)
		private Integer museumSeq;

		@Schema(description = "박물관_이름")
		private String museumName;

		@Schema(description = "박물관_전화번호")
		private String museumTel;

		@Schema(description = "박물관_주소")
		private String museumLoc;

		@Schema(description = "디바이스_아이디")
		private Integer[] deviceSeq;

		@Schema(description = "박물관_로고파일")
		private MultipartFile file;
	}

	@Getter
	@Setter
	public static class museumDelete
	{
		@NotEmpty
		private Integer museumSeq;

		public museumDelete(Integer museumSeqs)
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

		@Schema(description = "등록된_카레고리_정보")
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
