package com.green.cloud.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.green.cloud.common.Result;
import com.green.cloud.entity.Role;
import com.green.cloud.mapper.RoleMapper;
import com.green.cloud.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    IRoleService roleService;

    @Autowired
    RoleMapper roleMapper;


    @GetMapping("/page")
    public Result page(@RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                       @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {

        Page<Role> pageInfo = new Page<>(currentPage, pageSize);
        Page<Role> page = roleService.page(pageInfo);

        long total = page.getTotal();
        List<Role> records = page.getRecords();
        List<Role> roles = records.stream().peek(role -> {
                    List<Integer> permissionIds = roleMapper.getPermissionsByRoleId(role.getId());

                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    role.setPermissionIds(permissionIds);
                    role.setFormatCreateTime(role.getCreateTime().format(dateTimeFormatter));
                    role.setFormatUpdateTime(role.getUpdateTime().format(dateTimeFormatter));
                }

        ).toList();


        return Result.ok(roles, total);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Role role) {

        return roleService.addRole(role);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        return roleService.deleteById(id);
    }

    @PostMapping("/addPermission")
    public Result addRolePermission(String role, List<Integer> permissions) {
        return roleService.addRolePermission(role, permissions);
    }

    /**
     * 参数传递问题
     * @RequestBody 注解必须要添加
     */
    @PostMapping("/updatePermission")
    public Result updateRolePermission(@RequestBody Role role) {
        Integer roleId = role.getId();
        List<Integer> newPermission = role.getPermissionIds();
        return roleService.updateRolePermission(roleId, newPermission);
    }


    @GetMapping("/permission")
    public Result listPermission() {
        return roleService.listPermission();
    }

}
