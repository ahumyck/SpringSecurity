package com.ctf.jwtjku.model.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	@ToString.Exclude
	private long id;
	private String roleName;

	public Role(String roleName) {
		this.roleName = roleName;
	}

}
