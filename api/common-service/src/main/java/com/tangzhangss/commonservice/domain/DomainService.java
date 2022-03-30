package com.tangzhangss.commonservice.domain;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.FileUtil;
import com.tangzhangss.commonutils.utils.ListUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *域名服务
 */
@Service
public class DomainService extends SysBaseService<DomainEntity,DomainDao>{
    @Override
    protected void beforeSaveData(DomainEntity data) {
        ExceptionUtil.throwException("not_found");
    }
}
