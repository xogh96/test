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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tb_museum")
public class MuseumEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mu_seq", columnDefinition = "INT COMMENT '박물관_시퀀스' ")
	private Integer museumSeq;

	@Column(name = "mu_nm", columnDefinition = "VARCHAR(100) COMMENT '박물관_이름' ")
	private String museumName;

	@Column(name = "mu_tel", columnDefinition = "VARCHAR(20) COMMENT '박물관_전화번호' ")
	private String museumTel;

	@Column(name = "mu_loc", columnDefinition = "VARCHAR(300) COMMENT '박물관_주소' ")
	private String museumLoc;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "museumEntity")
	@JsonBackReference
	private List<CategoryEntity> categoryEntity = new ArrayList<CategoryEntity>();

	// @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH , CascadeType.REFRESH},
	// fetch = FetchType.LAZY, mappedBy = "museumEntity")
	// private List<DeviceEntity> deviceEntity = new ArrayList<DeviceEntity>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_museum_device", joinColumns = @JoinColumn(name = "mu_seq"), inverseJoinColumns = @JoinColumn(name = "dev_seq"))
	private List<DeviceEntity> deviceEntity = new ArrayList<DeviceEntity>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_seq", referencedColumnName = "file_seq", foreignKey = @ForeignKey(name = "fk_tb_museum_2"))
	@JsonManagedReference
	private ContentFileEntity contentFileEntity;
}