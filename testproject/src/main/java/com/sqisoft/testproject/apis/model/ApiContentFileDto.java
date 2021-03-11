package com.sqisoft.testproject.apis.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.DeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "파일")
public class ApiContentFileDto
{
	@Getter
	@Setter
	@AllArgsConstructor
	public static class contentFileInfo
	{
		private Integer fileSeq;

		private String fileName;

		private String filePhyName;

		private String fileThumbPhyName;

		private Long fileSize;

		private String fileContentType;

		private Integer fileOrder;

		public contentFileInfo(ContentFileEntity contentFileEntity)
		{
			fileSeq = contentFileEntity.getFileSeq();
			fileName = contentFileEntity.getFileName();
			filePhyName = contentFileEntity.getFilePhyName();
			fileThumbPhyName = contentFileEntity.getFileThumbPhyName();
			fileSize = contentFileEntity.getFileSize();
			fileContentType = contentFileEntity.getFileContentType();
			fileOrder = contentFileEntity.getFileOrder();
		}

	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class contentFileLogo
	{
		private Integer fileSeq;

		private String fileName;

		private String filePhyName;

		private String fileThumbPhyName;

		private Long fileSize;

		private String fileContentType;

		private Integer fileOrder;

		public contentFileLogo(ContentFileEntity contentFileEntity)
		{
			fileSeq = contentFileEntity.getFileSeq();
			fileName = contentFileEntity.getFileName();
			filePhyName = contentFileEntity.getFilePhyName();
			fileThumbPhyName = contentFileEntity.getFileThumbPhyName();
			fileSize = contentFileEntity.getFileSize();
			fileContentType = contentFileEntity.getFileContentType();
			fileOrder = contentFileEntity.getFileOrder();
		}

	}

}
