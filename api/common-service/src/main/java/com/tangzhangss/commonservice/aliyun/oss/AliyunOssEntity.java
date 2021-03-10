package com.tangzhangss.commonservice.aliyun.oss;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * endpoint
 * bucket
 * 属性请不要更改
 * （更改之后已经存过的图片删除会找不到文件）
 */
@Entity
@Table(name = "tbl_common_service_aliyun")
@Data
public class AliyunOssEntity extends SysBaseEntity {
    //EndPoint（地域节点）
    private String endpoint = "";
    //oss用户_非主用户
    private String accessKeyID = "";
    //oss用户_非主用户
    private String accessKeySecret = "";
    //图片样式-需要自己带上分隔符，如默认:?x-oss-process=style/pictureStyle或则_pictureStyle
    private String pictureStyle = "";
    //桶名
    private String bucket = "";
    //域名...地址
    @Transient
    private String urlStr = "";

    @Transient
    public static AliyunOssEntity defaultConfig = new AliyunOssEntity();

    static {
        defaultConfig.endpoint = "oss-cn-chengdu.aliyuncs.com";
        defaultConfig.accessKeyID = "LTAIyEbHDEEdSrH5";
        defaultConfig.accessKeySecret = "C9fdLHsaSp6UJAkwsLJ3EPdZIEnE7Z";
        defaultConfig.pictureStyle = "_custom";
        defaultConfig.bucket = "my-admin-v2";
    }

    public String getUrlStr() {
        return "https://" + bucket + "." + endpoint;
    }
}
