package com.sqisoft.testproject.api.model;

import com.sqisoft.testproject.domain.ContentFileEntity;

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
		@Schema(description = "파일_아이디")
		private Integer fileSeq;

		@Schema(description = "파일_실제_이름")
		private String fileName;

		@Schema(description = "파일_저장된_이름")
		private String filePhyName;

		@Schema(description = "파일_썸네일_저장된_이름")
		private String fileThumbPhyName;

		@Schema(description = "파일_사이즈")
		private Long fileSize;

		@Schema(description = "파일_타입")
		private String fileContentType;

		@Schema(description = "파일_순서")
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
		@Schema(description = "파일_아이디")
		private Integer fileSeq;

		@Schema(description = "파일_실제_이름")
		private String fileName;

		@Schema(description = "파일_저장된_이름")
		private String filePhyName;

		@Schema(description = "파일_썸네일_저장된_이름")
		private String fileThumbPhyName;

		@Schema(description = "파일_사이즈")
		private Long fileSize;

		@Schema(description = "파일_타입")
		private String fileContentType;

		@Schema(description = "파일_순서")
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
