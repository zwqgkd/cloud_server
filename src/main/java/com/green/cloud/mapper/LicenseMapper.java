package com.green.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.green.cloud.entity.License;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;
public interface LicenseMapper extends BaseMapper<License> {



    @Select("SELECT * FROM license WHERE licensename = #{licensename}")
    List<License> selectBylicenseName(String licensename);

    @Select("SELECT licensename FROM license WHERE licensekey = #{licensekey} LIMIT 1")
    String hasLicenseKey(String licensekey);

    @Delete("DELETE FROM license WHERE licensename = #{LicenseName}")
    int deleteByLicenseName(String licenseName);
}