package com.green.cloud.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.cloud.entity.User;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/demo")
@RestController
public class DemoController {
    private final String userJsonPath = "D:\\新建文件夹\\GeliVision\\src\\登录窗口\\login.json";
    @PostMapping("/login1")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User loginForm) {
        try {
            // 读取JSON文件并将其转换为用户对象列表
            List<User> users = readUsersFromJson();

            // 验证用户名和密码是否匹配
            boolean isAuthenticated = authenticateUser(loginForm, users);

            if (isAuthenticated) {
                // 用户验证成功
                return ResponseEntity.ok().body("登录成功");
            } else {
                // 用户验证失败
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (IOException e) {
            // 处理文件读取或JSON解析错误
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    private List<User> readUsersFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(userJsonPath);
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    private boolean authenticateUser(User loginForm, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(loginForm.getUsername()) &&
                    user.getPassword().equals(loginForm.getPassword())) {
                return true;
            }
        }
        return false;
    }



}

