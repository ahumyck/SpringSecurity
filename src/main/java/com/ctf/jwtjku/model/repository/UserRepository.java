package com.ctf.jwtjku.model.repository;

import com.ctf.jwtjku.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
