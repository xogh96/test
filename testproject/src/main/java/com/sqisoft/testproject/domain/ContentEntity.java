package com.sqisoft.testproject.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_content")
public class ContentEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cont_seq", columnDefinition = "INT COMMENT '콘텐츠_시퀀스' ")
	private Integer contentSeq;

	@Column(name = "cont_nm", columnDefinition = "VARCHAR(100) COMMENT '콘텐츠_이름' ")
	private String contentName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cate_seq", referencedColumnName = "cate_seq", foreignKey = @ForeignKey(name = "fk_tb_content_1"))
	private CategoryEntity categoryEntity;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_seq", referencedColumnName = "file_seq", foreignKey = @ForeignKey(name = "fk_tb_content_2"))
	private ContentFileEntity contentFileEntity;
}
