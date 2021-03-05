package com.ctf.jwtjku.model.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private long id;
	private String roleName;

	public Role(String roleName) {
		this.roleName = roleName;
	}

}
