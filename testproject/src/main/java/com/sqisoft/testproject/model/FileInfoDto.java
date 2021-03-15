package com.sqisoft.testproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileInfoDto
{
	// 파일 저장
	private String fileName;
	private String filePhyName;
	private String fileContentType;
	private Long fileSize;
	private Integer fileOrder;
	private String fileThumbPhyName;
}
