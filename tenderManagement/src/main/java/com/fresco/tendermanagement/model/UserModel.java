package com.fresco.tendermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "Username")
	private String username;
	@Column(name = "Companyname")
	private String companyName;
	@Column(name = "password")
	private String password;
	@Column(name = "email",unique= true)
	private String email;
	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "id")
	private RoleModel role;
	
}
