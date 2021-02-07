package com.tangzhangss.commondata.album;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 备忘录
 */
@Entity
@Table(name = "tbl_common_data_album")
@Data
public class AlbumEntity extends SysBaseEntity {
    private String title;

    /*
    相册内容 url||url2||url3
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    @Transient
    private String imageConnector="&:&";//图片链接符号

    @Transient
    private String [] images=new String[]{};

    public String[] getImages() {
        if(StringUtils.isNotBlank(content)){
            images = content.split(imageConnector);
        }
        return images;
    }

    public void setImages(String[] images) {
        this.content = StringUtils.join(images,imageConnector);
    }
}
