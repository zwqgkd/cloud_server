package com.green.cloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.generator.UUIDGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.green.cloud.common.Result;
import com.green.cloud.common.UserHolder;
import com.green.cloud.entity.Role;
import com.green.cloud.entity.UserAuth;
import com.green.cloud.mapper.RoleMapper;
import com.green.cloud.mapper.UserMapper;
import com.green.cloud.entity.User;
import com.green.cloud.service.IRoleService;
import com.green.cloud.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleService roleService;



    public boolean  getByEmailAndPassword(String email, String password) {
        return userMapper.getByEmailAndPassword(email, password) != null;
    }
    public String getRole(String email, String password){
        return userMapper.getRole(email, password);
    }



    @Override
    public Result login(String email, String password, HttpSession session) {

        User user = query().eq("email", email).one();
        if (user == null){
            return Result.fail("用户不存在");
        }

        if (!password.equals(user.getPassword())){
            return Result.fail("用户名或密码错误");
        }

        UserAuth userAuth = new UserAuth();
        BeanUtil.copyProperties(user, userAuth);
        Role role = roleService.getById(user.getRoleId());
        userAuth.setRole(role.getRole());

        String token = UUID.randomUUID().toString();
        userAuth.setToken(token);
        session.setAttribute(token, userAuth);

        UserHolder.saveUser(userAuth);

        // 登录成功应该返回token和用户基本信息
        return Result.ok("登录成功", userAuth);
    }

    @Override
    public Result removeUserById(Long id) {

        boolean isRemoved = removeById(id);
        if (isRemoved){
            return Result.ok("用户删除成功");
        }
        return Result.fail("用户删除失败");
    }

    @Override
    public Result updateUser(User user) {
        boolean isUpdate = updateById(user);
        if (!isUpdate){
            return Result.fail("更新失败");
        }
        return Result.ok("更新成功");
    }

    @Override
    public Result addUser(User user) {
        user.setPassword("Gree2024");

        boolean isSave = save(user);
        if (!isSave){
            return Result.fail("新增用户失败");
        }
        return Result.ok("新增用户成功");
    }

    @Override
    public Result resetPassword(Long userId) {
        boolean isReset = update().setSql("password = 'Gree2024'").eq("id", userId).update();
        if (isReset){
            return Result.ok("重置密码成功");
        }
        return Result.fail("重置密码失败");
    }
}
