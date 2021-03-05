package com.ctf.jwtjku.model.repository;

import com.ctf.jwtjku.model.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findAll();

	Role findByRoleName(String roleName);

	<S extends Role> S save(S role);
}
