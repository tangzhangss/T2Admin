package com.tangzhangss.commonservice.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.utils.FileUtil;
import com.tangzhangss.commonutils.utils.ListUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 阿里云 oss文件操作
 */
@Service
public class AliyunOssService extends SysBaseService<AliyunOssEntity, AliyunOssDao> {

    public void deleteFile(List<String> filePaths){

    }

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
        if (aliyunOss != null) return aliyunOss;
        //如果没有就返回默认
        return AliyunOssEntity.defaultConfig;
    }


    public String uploadPicture(MultipartFile file) throws Exception {
        String fileName = getFilePath() + "/"+file.getOriginalFilename();
        AliyunOssEntity aliyunOss = getAliyunOssConfig();
        return this.upload(fileName, file.getInputStream(), aliyunOss) + aliyunOss.getPictureStyle();
    }

    public String uploadPictureDeletable(MultipartFile file)throws Exception{
        String fileName = getFilePath() + "/"+System.currentTimeMillis()+"_"+file.getOriginalFilename();
        AliyunOssEntity aliyunOss = getAliyunOssConfig();
        return this.upload(fileName,file.getInputStream(), aliyunOss) + aliyunOss.getPictureStyle();
    }

    public String uploadPictureNoAuth(MultipartFile file) throws Exception {
        String fileName = "noAuth/" + file.getOriginalFilename();
        AliyunOssEntity aliyunOss = AliyunOssEntity.defaultConfig;
        return this.upload(fileName, file.getInputStream(), aliyunOss) + aliyunOss.getPictureStyle();
    }

    /*
      https://my-admin-v2.oss-cn-chengdu.aliyuncs.com/861136242/picture/27117392470016/1614511951639_2020LOGO.png
         桶名: my-admin-v2
         endpoint:oss-cn-chengdu.aliyuncs.com
         filePath:后面的
     */
    public void deleteByUrl(List<String> urls) {
        ListUtil listUtil = ListUtil.createArrayList();
        AliyunOssEntity aliyunOssConfig = getAliyunOssConfig();
        Pattern pattern = Pattern.compile("^https?://.*?\\..*?/(.*)"+ aliyunOssConfig.getPictureStyle()+"$");
        urls.forEach(url->{
            Matcher matcher = pattern.matcher(url);
            matcher.matches();
            listUtil.add(matcher.group(1));
        });
        this.delete(listUtil.get(),aliyunOssConfig);
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
            //先判断文件是否存在如果存在获取文件的名字
            fileName = getOssFileName(fileName,ossClient,aliyunOss,inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ossClient.shutdown();
            inputStream.close();
        }
        return aliyunOss.getUrlStr() + "/" + fileName;
    }


    /**
     * 批量删除文件
     * @param keys (文件路径)
     * 如果找不到key不会抛出异常
     */
    private void delete(List<String> keys, AliyunOssEntity aliyunOss) {
        // 创建OSSClient实例。
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(aliyunOss.getEndpoint(), aliyunOss.getAccessKeyID(), aliyunOss.getAccessKeySecret());
            // 删除文件。
            ossClient.deleteObjects(new DeleteObjectsRequest(aliyunOss.getBucket()).withKeys(keys));
        } catch (Exception e) {
           throw e;
        } finally {
            ossClient.shutdown();
        }
    }
    /*
    文件名一样的话
    计算上传的文件名
     */
    private String getOssFileName(String fileName,OSSClient ossClient,AliyunOssEntity aliyunOssEntity,InputStream inputStream) throws IOException {
        int offset = 0;
        String fileNameTemp = fileName;
        do {
            //第一次不加偏移量
            if(offset!=0){
                fileName =fileNameTemp.substring(0,fileNameTemp.lastIndexOf("."))+"_"+offset+fileNameTemp.substring(fileNameTemp.lastIndexOf("."));
            }
            boolean found = ossClient.doesObjectExist(aliyunOssEntity.getBucket(),fileName);
            if(found){
                OSSObject ossObject = ossClient.getObject(aliyunOssEntity.getBucket(),fileName);
                //如果存在判断是否同一个文件
                InputStream temp = FileUtil.cloneInputStream(ossObject.getObjectContent());
                if(FileUtil.isSameFile(temp,inputStream)){
                    //是同一个文件
                    temp.close();
                    return  fileName;
                }else{
                    temp.close();
                    //不是同一个文件
                    offset++;
                }
            }else{//找不到返回null
                //上传
                ossClient.putObject(aliyunOssEntity.getBucket(), fileName, inputStream);
                return fileName;
            }
        }while (true);
    }


}
