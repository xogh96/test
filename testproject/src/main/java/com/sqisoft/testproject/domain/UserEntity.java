package com.sqisoft.testproject.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq", columnDefinition = "INT COMMENT '유저_시퀀스' ")
	private Integer userSeq;

	@Column(name = "user_id", columnDefinition = "VARCHAR(30) COMMENT '유저_아이디'", unique = true)
	private String userId;

	@Column(name = "user_pw", columnDefinition = "VARCHAR(100) COMMENT '유저_비밀번호'")
	private String userPassword;

}
