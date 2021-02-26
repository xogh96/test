package com.sqisoft.testproject.domain;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_category")
public class CategoryEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cate_seq", columnDefinition = "INT COMMENT '카테고리_시퀀스' ")
	private Integer categorySeq;

	@Column(name = "cate_nm", columnDefinition = "VARCHAR(100) COMMENT '카테고리_이름' ")
	private String categoryName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mu_seq", referencedColumnName = "mu_seq", foreignKey = @ForeignKey(name = "fk_tb_category_1"))
	@JsonManagedReference
	private MuseumEntity museumEntity;

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "categoryEntity")
	private List<ContentEntity> contentEntity = new ArrayList<ContentEntity>();
}
