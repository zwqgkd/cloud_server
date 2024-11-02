package com.green.cloud.controller;


import cn.hutool.core.util.StrUtil;
import com.green.cloud.common.Result;
import com.green.cloud.common.SystemConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;



@RestController
@RequestMapping("/common")
public class CommonController {




    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()){
            System.out.println("文件内容为空");
            return Result.fail("文件内容为空");
        }

        try {
            // 获取原始文件名称
            String originalFilename = file.getOriginalFilename();
            // 生成新文件名
            String fileName = createNewFileName(originalFilename);
            // 保存文件，SystemConstants.IMAGE_UPLOAD_DIR文件保存的地址，存放在Nginx文件夹下
            file.transferTo(new File(SystemConstants.UPLOAD_BASE_PATH, fileName));
            // 返回结果

            return Result.ok(fileName);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }


    private String createNewFileName(String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();

        // 判断目录是否存在
        File dir = new File(SystemConstants.UPLOAD_BASE_PATH, StrUtil.format("/gree/{}", suffix));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format("/gree/{}/{}.{}",suffix, name, suffix);
    }

}
