package com.green.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.green.cloud.common.Result;
import com.green.cloud.entity.Role;

import java.util.List;

public interface IRoleService extends IService<Role> {


    Result addRole(Role role);

    Result addRolePermission(String role, List<Integer> permissionIds);

    Result updateRolePermission(Integer roleId, List<Integer> newPermission);


    Result listPermission();

    Result deleteById(Integer id);

    Result getPermissionsByRoleId(Integer roleId);
}
