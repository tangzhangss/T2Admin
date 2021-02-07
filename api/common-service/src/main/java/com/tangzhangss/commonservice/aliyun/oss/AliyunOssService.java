package com.tangzhangss.commonservice.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 阿里云 oss文件操作
 */
@Service
public class AliyunOssService extends SysBaseService<AliyunOssEntity, AliyunOssDao> {

    /**
     * 获取文件路径 path
     *
     * @return
     */
    private String getFilePath() {
        String clientId = SysContext.getClientId();
        long userId = SysContext.getUserId();
        String path = clientId + "/picture/" + userId;
        return path;
    }

    /**
     * 获取阿里云oss的配置
     *
     * @return
     */
    private AliyunOssEntity getAliyunOssConfig() {
        String clientId = SysContext.getClientId();
        //获取当前租户下的配置
        AliyunOssEntity aliyunOss = getOneWithMapString("clientId@EQ=" + clientId);
        if (aliyunOss == null) return new AliyunOssEntity();
        //如果没有就返回默认
        return AliyunOssEntity.defaultConfig;
    }


    public String uploadPicture(MultipartFile file) throws Exception {
        String fileName = getFilePath() + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        AliyunOssEntity aliyunOss = getAliyunOssConfig();
        return this.upload(fileName, file.getInputStream(), aliyunOss) + aliyunOss.getPictureStyle();
    }

    public String uploadPictureNoAuth(MultipartFile file) throws Exception {
        String fileName = "noAuth/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        AliyunOssEntity aliyunOss = AliyunOssEntity.defaultConfig;
        return this.upload(fileName, file.getInputStream(), aliyunOss) + aliyunOss.getPictureStyle();
    }

    /**
     * 上传文件
     *
     * @param fileName    文件全路径,不能包含回车、换行、以及xml1.0不支持的字符，同时也不能以“/”或者“\”开头
     * @param inputStream 文件流
     * @return
     */
    private String upload(String fileName, InputStream inputStream, AliyunOssEntity aliyunOss) throws Exception {
        // 创建OSSClient实例。
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(aliyunOss.getEndpoint(), aliyunOss.getAccessKeyID(), aliyunOss.getAccessKeySecret());

            ossClient.putObject(aliyunOss.getBucket(), fileName, inputStream);

            return aliyunOss.getUrlStr() + "/" + fileName;

        } catch (Exception e) {
            throw e;
        } finally {
            ossClient.shutdown();
        }
    }


}
