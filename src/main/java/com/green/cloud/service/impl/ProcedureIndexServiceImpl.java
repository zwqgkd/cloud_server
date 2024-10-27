package com.green.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.green.cloud.mapper.ProcedureIndexMapper;
import com.green.cloud.entity.ProcedureIndex;
import com.green.cloud.service.IProcedureIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureIndexServiceImpl extends ServiceImpl<ProcedureIndexMapper, ProcedureIndex> implements IProcedureIndexService {

}
