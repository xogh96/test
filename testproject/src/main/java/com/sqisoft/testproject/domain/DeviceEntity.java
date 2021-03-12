package com.sqisoft.testproject.domain;

import java.io.Serializable;
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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "tb_device")
public class DeviceEntity implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dev_seq", columnDefinition = "INT COMMENT '디바이스_시퀀스' ")
	private Integer deviceSeq;

	@Column(name = "dev_nm", columnDefinition = "VARCHAR(100) COMMENT '디바이스_이름' ")
	private String deviceName;

	@Column(name = "dev_cd", columnDefinition = "VARCHAR(50) COMMENT '디바이스_코드' ")
	private String deviceCode;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "mu_seq", referencedColumnName = "mu_seq", foreignKey = @ForeignKey(name = "fk_device_1"))
//	private MuseumEntity museumEntity;
	
}
