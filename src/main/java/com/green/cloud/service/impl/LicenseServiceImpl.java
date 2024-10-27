package com.green.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.green.cloud.mapper.LicenseMapper;
import com.green.cloud.mapper.UserLicenseMapper;
import com.green.cloud.entity.License;
import com.green.cloud.entity.UserLicense;
import com.green.cloud.service.ILicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseServiceImpl extends ServiceImpl<LicenseMapper, License> implements ILicenseService {
    @Autowired
    LicenseMapper licenseMapper;

    @Autowired
    UserLicenseMapper user_licenseMapper;



    public String hasLicenseKey(String licensekey){return licenseMapper.hasLicenseKey(licensekey);}

    public int match(UserLicense record){return user_licenseMapper.insert(record);}

    public List<License> selectBylicenseName(String licenseName){return licenseMapper.selectBylicenseName(licenseName);}

    public List<String> select(String username){return user_licenseMapper.select(username);}


    @Override
    public void removeByName(String name) {
        UpdateWrapper<License> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("license_name", name);
        remove(updateWrapper);
    }
}
