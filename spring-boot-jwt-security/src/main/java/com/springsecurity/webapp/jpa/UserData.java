package com.springsecurity.webapp.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_DATA")
@Getter
@Setter
public class UserData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "USERNAME")
	private String username;
	
	private String password;
	
	private String roles;
	
	private boolean active;

}
