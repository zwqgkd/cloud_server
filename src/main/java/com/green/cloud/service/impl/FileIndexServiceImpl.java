package com.green.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.green.cloud.mapper.FilesMapper;
import com.green.cloud.entity.File;
import com.green.cloud.service.IFileIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileIndexServiceImpl extends ServiceImpl<FilesMapper, File> implements IFileIndexService {





    @Override
    public File queryByFileName(String fileName) {

        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("file_name", fileName);
        return getOne(fileQueryWrapper);
    }

    @Override
    public Integer queryFatherIdByFileName(String fileName) {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("file_name", fileName);
        File file = getOne(fileQueryWrapper);
        return file.getFatherId();
    }

    @Override
    public File queryByFatherId(Integer fatherId) {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("father_id", fatherId);
        return getOne(fileQueryWrapper);
    }

    @Override
    public void deleteByFileName(String fileName) {
        UpdateWrapper<File> fileUpdateWrapper = new UpdateWrapper<>();
        fileUpdateWrapper.eq("file_name", fileName);
        remove(fileUpdateWrapper);
    }
}
