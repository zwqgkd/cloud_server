package com.green.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.green.cloud.entity.UserLicense;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserLicenseMapper extends BaseMapper<UserLicense> {

    @Select("SELECT licensename FROM user_license WHERE username = #{username}")
    List<String> select(String username);

    @Delete("DELETE FROM user_license WHERE licensename = #{licenseName}")
    int deleteByLicenseName(String licenseName);
}