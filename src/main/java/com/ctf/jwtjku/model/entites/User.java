package com.ctf.jwtjku.model.entites;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private long id;

	@Column(unique = true)
	private String username;
	private String password;

	@ManyToOne
	private Role role;

	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}



