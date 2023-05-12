package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AuthRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
