package com.t2admin.service;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.t2admin.dao.ReportFileDao;
import com.t2admin.entity.ReportFile;
import org.springframework.stereotype.Service;

@Service("reportFileService")
public class ReportFileServiceImpl extends ServiceImpl<ReportFileDao, ReportFile> {

}
