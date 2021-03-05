package com.ctf.jwtjku.model.repository;

import com.ctf.jwtjku.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();

	User findByUsername(String username);

	<S extends User> S save(S user);
}
