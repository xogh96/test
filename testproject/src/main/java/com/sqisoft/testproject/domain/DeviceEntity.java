package com.sqisoft.testproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "mu_seq", referencedColumnName = "mu_seq", foreignKey = @ForeignKey(name = "fk_device_1"))
//	private MuseumEntity museumEntity;
	
}
