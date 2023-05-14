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

    @Select("CALL sign_up(#{username}, #{password}, #{name})")
    @Options(statementType = StatementType.CALLABLE)
    String signUp(String username, String password, String name);

    @Select("CALL user_update(#{userId}, #{name}, #{introduce}, #{imageUrl})")
    @Options(statementType = StatementType.CALLABLE)
    String updateUserInfo(int userId, String name, String introduce, String imageUrl);
}
