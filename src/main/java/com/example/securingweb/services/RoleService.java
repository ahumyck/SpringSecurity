package com.example.securingweb.services;

import com.example.securingweb.entites.Role;
import com.example.securingweb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public boolean createRole(String roleName) {
		if (roleRepository.findByRoleName(roleName) == null) {
			return roleRepository.save(new Role(roleName)) != null;
		}
		return false;
	}

	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

}
