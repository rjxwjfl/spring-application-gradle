package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
