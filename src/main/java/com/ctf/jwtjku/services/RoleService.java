package com.ctf.jwtjku.services;

import com.ctf.jwtjku.model.entites.Role;
import com.ctf.jwtjku.model.repository.RoleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public boolean createRole(String roleName) {
        if (roleRepository.findByRoleName(roleName) == null) {
            roleRepository.save(new Role(roleName));
            return true;
        }
        return false;
    }

    public Role findRole(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

}
