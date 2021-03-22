package com.ctf.jwtjku.model.repository;

import com.ctf.jwtjku.model.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
