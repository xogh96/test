package com.sqisoft.testproject.apis.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiContentFileDto
{

	@Getter
	@Setter
	public static class save
	{
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
	@AllArgsConstructor
	public static class info
	{
		private Integer fileSeq;

		private String fileName;

		private String filePhyName;

		private String fileThumbPhyName;

		private Long fileSize;

		private String fileContentType;

		private Integer fileOrder;

		public info(ContentFileEntity contentFileEntity)
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
