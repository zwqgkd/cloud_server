package com.green.cloud.controller;

import com.green.cloud.common.Result;
import com.green.cloud.entity.License;
import com.green.cloud.service.ILicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/license")
@RestController
public class LicenseController {

    @Autowired
    private ILicenseService licenseService;

    @GetMapping("/list")
    public List<License> list() {
        return licenseService.list();
    }

    @PostMapping("/deleteLicense/{licenseName}")
    public Result deleteLicense(@PathVariable String licenseName) {
        licenseService.removeByName(licenseName);
        return Result.ok("删除成功");

    }

}
