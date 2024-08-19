package com.sookmyung.concon.User.repository;

import com.sookmyung.concon.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
