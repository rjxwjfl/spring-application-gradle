package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.UserOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface UserRepo {
    @Select("CALL get_user_outline(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<UserOutline> getUserOutline(Integer userId);
}