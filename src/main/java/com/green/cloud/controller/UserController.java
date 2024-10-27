package com.green.cloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.green.cloud.common.Result;
import com.green.cloud.common.UserHolder;
import com.green.cloud.entity.LoginForm;
import com.green.cloud.entity.User;
import com.green.cloud.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


@RequestMapping("/user")
@RestController
public class UserController {

    private static final HashMap<Integer, String> USER_TYPE = new HashMap<>();

    static {
        USER_TYPE.put(1, "超级管理员");
        USER_TYPE.put(2, "管理员");
        USER_TYPE.put(3, "普通用户");
    }

    @Autowired
    private IUserService userService;


    @GetMapping("/page")
    public Result page(@RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                       @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {

        Page<User> pageInfo = new Page<>(currentPage, pageSize);
        Page<User> page = userService.page(pageInfo);

        long total = page.getTotal();
        List<User> records = page.getRecords();

        List<User> users = records.stream().peek(user -> {
                    user.setRole(USER_TYPE.get(user.getRoleId()));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    user.setFormatCreateTime(user.getCreateTime().format(dateTimeFormatter));
                    user.setFormatUpdateTime(user.getUpdateTime().format(dateTimeFormatter));
                }

        ).toList();


        return Result.ok(users, total);
    }

    /**
     * Admin才能进行操作
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Result deleteUserById(@PathVariable Long id) {

        return userService.removeUserById(id);
    }

    /**
     * Admin才能进行操作
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/resetPassword/{id}")
    public Result resetPassword(@PathVariable("id") Long userId){
        return userService.resetPassword(userId);
    }


    /**
     * @param loginForm 前端传输过来的loginForm中的username和password不能为空
     * @return 登录成功后返回必要用户信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpSession session) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        return userService.login(email, password);
    }


    @PostMapping("/register")
    public Result register(@RequestBody LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
        User user = new User(); // 默认为普通用户
        user.setEmail(email);
        user.setPassword(password);


        if (userService.save(user)) {
            System.out.println("注册成功");

            return Result.ok("注册成功");

        } else {
            return Result.fail("注册失败");
        }

    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request, HttpSession session) {


        if (UserHolder.getUser() != null) {
            UserHolder.removeUser();
        }

        // 注销用户，清空Session中的用户信息
        if (session != null) {
            System.out.println("%%%%" + session.getAttribute("userInfo"));
            session.invalidate(); // 销毁 session
            String token = request.getHeader("token");
            System.out.println(token);
            if (token != null) {
//                stringRedisTemplate.delete(token); // 删除 Redis 中的 token/
                // 改为使用session存储token
            }
            return Result.ok("退出成功");
        } else {
            return Result.fail("未登录");
        }


    }


}
