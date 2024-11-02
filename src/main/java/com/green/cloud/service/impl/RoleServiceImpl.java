package com.green.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.green.cloud.common.Result;
import com.green.cloud.entity.Permission;
import com.green.cloud.entity.Role;
import com.green.cloud.mapper.RoleMapper;
import com.green.cloud.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Autowired
    RoleMapper roleMapper;

    /**
     * 修改tb_role和tb_role_permission
     *
     * @param role        角色名
     * @param createUser
     * @param permissions 权限id集合
     * @return
     */
    @Transactional
    @Override
    public Result addRole(Role role) {

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role", role.getRole());
        long count = count(roleQueryWrapper);
        if (count > 0) {
            return Result.fail("角色已存在！");
        }

        save(role);

        Integer roleId = role.getId();
        for (Integer permission : role.getPermissionIds()) {
            roleMapper.addRolePermissions(roleId, permission);
        }

        return Result.ok("角色新增成功");
    }

    /**
     * 给已有角色新增权限
     *
     * @param role
     * @param permissionIds
     * @return
     */
    @Override
    public Result addRolePermission(String role, List<Integer> permissionIds) {
        return null;
    }

    @Override
    public Result updateRolePermission(Integer roleId, List<Integer> newPermission) {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("id", roleId);
        Role role = getOne(roleQueryWrapper);
        if (role == null) {
            return Result.fail("角色不存在");
        }
        // 删除旧权限，添加新权限
        roleMapper.removePermissionByRoleId(roleId);

        for (Integer permission : newPermission) {
            roleMapper.addRolePermissions(roleId, permission);
        }


        return Result.ok("角色修改成功");
    }

    @Override
    public Result listPermission() {

        List<Permission> permissions = roleMapper.listPermission();
        return Result.ok(permissions, Long.valueOf(permissions.size()));
    }

    @Transactional
    @Override
    public Result deleteById(Integer id) {

        Role role = getById(id);
        if (role == null) {
            return Result.fail("角色不存在");
        }

        removeById(id);
        roleMapper.removePermissionByRoleId(id);

        return Result.ok("角色删除成功");
    }

    @Override
    public Result getPermissionsByRoleId(Integer roleId) {
        List<Integer> permissionsByRoleId = roleMapper.getPermissionsByRoleId(roleId);
        return Result.ok(permissionsByRoleId, Long.valueOf(permissionsByRoleId.size()));
    }


}
