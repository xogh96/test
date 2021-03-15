package com.sqisoft.testproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_contentfile")
public class ContentFileEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_seq", columnDefinition = "INT COMMENT '파일_시퀀스' ")
	private Integer fileSeq;

	@Column(name = "file_nm", columnDefinition = "VARCHAR(200) COMMENT '파일명'")
	private String fileName;

	@Column(name = "file_phy_nm", columnDefinition = "VARCHAR(200) COMMENT '물리_파일명'")
	private String filePhyName;
	
	@Column(name = "file_thumb_phy_nm", columnDefinition = "VARCHAR(200) COMMENT '썸네일_물리_파일명'")
	private String fileThumbPhyName;

	@Column(name = "file_size", columnDefinition = "BIGINT COMMENT '파일_사이즈'")
	private Long fileSize;
	
	@Column(name = "file_content_type", columnDefinition = "VARCHAR(200) COMMENT '파일_콘텐츠_타입'")
	private String fileContentType;

	@Column(name = "file_order", columnDefinition = "VARCHAR(20) COMMENT '파일_순서'")
	private Integer fileOrder;

	@OneToOne(mappedBy = "contentFileEntity")
	private ContentEntity contentEntity;

	@OneToOne(mappedBy = "contentFileEntity")
	@JsonBackReference
	private MuseumEntity museumEntity;

}
