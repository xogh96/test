package com.sqisoft.testproject.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
public class DeviceDto
{

	// 디바이스 저장
	private Integer[] deviceSeq;
	private String deviceCode;
	private String deviceName;

	// museum 저장
	private String museumName;
	private String museumTel;
	private String museumLoc;

	// 파일 저장
	private String fileName;

	private String filePhyName;

	private String fileContentType;

	private Long fileSize;

	private Integer fileOrder;

	private String fileThumbPhyName;

}
