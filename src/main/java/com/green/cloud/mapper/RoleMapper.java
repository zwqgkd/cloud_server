package com.green.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.green.cloud.entity.Permission;
import com.green.cloud.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {



    @Insert("insert into tb_role_permission(role_id, permission_id) values (#{roleId}, #{permissionId})")
    void addRolePermissions(Integer roleId, Integer permissionId);


    @Select("select permission_id from tb_role_permission where role_id = #{roleId}")
    List<Integer> getPermissionsByRoleId(Integer roleId);

    @Delete("delete from tb_role_permission where role_id = #{roleId}")
    int removePermissionByRoleId(Integer roleId);


    @Select("select * from tb_permission")
    List<Permission> listPermission();

}