package com.green.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.green.cloud.mapper.DirectoryIndexMapper;
import com.green.cloud.entity.DirectoryIndex;
import com.green.cloud.service.IDirectoryIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryIndexServiceImpl extends ServiceImpl<DirectoryIndexMapper, DirectoryIndex> implements IDirectoryIndexService {

}
