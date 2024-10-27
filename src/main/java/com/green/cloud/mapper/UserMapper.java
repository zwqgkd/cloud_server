package com.green.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.green.cloud.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {


    @Select("select * from user WHERE email = #{email} and password = #{password}")
    User getByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("select role from user WHERE email = #{email} and password = #{password}")
    String getRole(@Param("email") String email, @Param("password") String password);


    @Update("UPDATE user SET role = #{role} WHERE id = #{id}")
    int changeRoleByPrimaryKey(@Param("id") Integer id, @Param("role") String role);

}