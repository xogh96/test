package com.sqisoft.testproject.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "tb_device")
public class DeviceEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dev_seq", columnDefinition = "INT COMMENT '디바이스_시퀀스' ")
	private Integer deviceSeq;

	@Column(name = "dev_nm", columnDefinition = "VARCHAR(100) COMMENT '디바이스_이름' ")
	private String deviceName;

	@Column(name = "dev_cd", columnDefinition = "VARCHAR(50) COMMENT '디바이스_코드' ")
	private String deviceCode;
	
	@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true, mappedBy = "deviceEntity")
	@JsonBackReference
	private List<MuseumEntity> museumEntity = new ArrayList<MuseumEntity>();
	
}
