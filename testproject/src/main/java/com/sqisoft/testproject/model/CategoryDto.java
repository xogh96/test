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
public class CategoryDto
{

	// 디바이스 저장
	private String categoryName;

	// 박물관 이름
	private Integer museumSeq;

	// 박물관 이름
	private Integer deviceSeq;

}
