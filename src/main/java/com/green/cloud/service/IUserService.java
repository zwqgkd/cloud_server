package com.green.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.green.cloud.common.Result;
import com.green.cloud.entity.User;

public interface IUserService extends IService<User> {
    Result login(String email, String password);

    Result removeUserById(Long id);

    Result updateUser(User user);

    Result addUser(User user);

    Result resetPassword(Long userId);
}
