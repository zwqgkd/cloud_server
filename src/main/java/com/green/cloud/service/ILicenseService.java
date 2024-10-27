package com.green.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.green.cloud.entity.License;

public interface ILicenseService extends IService<License> {

    void removeByName(String name);

}
