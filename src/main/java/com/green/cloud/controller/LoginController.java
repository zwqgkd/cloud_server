package com.green.cloud.controller;

import com.green.cloud.common.Result;
import com.green.cloud.entity.*;
import com.green.cloud.service.impl.LicenseServiceImpl;
import com.green.cloud.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;


@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    LicenseServiceImpl licenseService;
    @Autowired
    UserServiceImpl userService;





    @PostMapping("/role")
    public String role(HttpServletRequest request, @RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        return userService.getRole(email, password);

    }



    @PostMapping("/license")
    public Result handleLicensePostRequest(@RequestBody LicenseRequest request) throws ParseException {
        System.out.println(Arrays.toString(request.getLabels()));
        String name = request.getName();
        String description = request.getDescription();
        String validity = request.getValidity();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date exp = dateFormat.parse(validity);
        Date createTime = new Date(new Date().getTime());
        String currentTime = Long.toString(new Date().getTime());
        String licenseKey = DigestUtils.md5Hex(currentTime);
        System.out.println(exp);
        for (String label : request.getLabels()) {
            License record = new License();
            record.setLicenseName(name);
            record.setDescription(description);
            record.setProcedureName(label);
            record.setLicenseKey(licenseKey);
            record.setCreateTime(createTime);
            record.setExp(exp);
            licenseService.save(record);
        }

        return Result.ok("创建成功");

    }

    @PostMapping("/licenseKey")
    public Result handleLicenseKey(@RequestBody Request licenseRequest) {
        String licenseKey = licenseRequest.getLicenseKey();
        String username = licenseRequest.getUsername();
        String licensename = licenseService.hasLicenseKey(licenseKey);
        if (licensename != null) {
            UserLicense user_license = new UserLicense();
            user_license.setUsername(username);
            user_license.setLicenseName(licensename);
            licenseService.match(user_license);

            return Result.ok("注册成功");
        }
        return Result.fail("注册码无效");

        // Process the license key and username
        // You can perform your desired logic here, such as saving the data to the database or performing any other actions


    }

    @GetMapping("/tableData/{username}")
    public List<License> getTableData(@PathVariable String username) {
        List<String> licenseNames = licenseService.select(username);
        List<License> allLicenses = new ArrayList<>();

        for (String licenseName : licenseNames) {
            List<License> licenses = licenseService.selectBylicenseName(licenseName);
            allLicenses.addAll(licenses);
        }
        return allLicenses;
    }



    // 定义请求体类，用于接收前端请求的数据

    @Data
    public static class Request {
        private String licenseKey;

        private String username;


    }

    @Data
    public static class LicenseRequest {
        private String[] labels;

        private String description;

        private String name;

        private String validity;
    }

}



