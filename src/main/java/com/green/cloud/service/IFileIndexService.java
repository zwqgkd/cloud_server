package com.green.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.green.cloud.entity.File;

public interface IFileIndexService extends IService<File> {


    File queryByFileName(String fileName);

    Integer queryFatherIdByFileName(String fileName);

    File queryByFatherId(Integer fatherId);

    void deleteByFileName(String fileName);
}
