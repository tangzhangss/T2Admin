package io.report.modules.material.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * 
 *
 * @date 2019-07-10 09:08:35
 */
 @TableName("material")
 public class MaterialEntity implements Serializable {
	 private static final long serialVersionUID = 1L;
 
	 /**
	  * 素材ID
	  */
	 //@TableId
	 private String materialId;
	 /**
	  * 素材名称
	  */
	 private String materialName;
	 /**
	  * 路径
	  */
	 private String materialPath;
	 /**
	  * 相对路径
	  */
	 private String materialRelativePath;
	 /**
	  * 标签
	  */
	 private String materialTags;
	 /**
	  * 分组
	  */
	 private String materialGroup;
	 /**
	  * 上传时间
	  */
	 private Date uploadTime;
	 /**
	  * 上传用户
	  */
	 private String uploadUser;
 
	 /**
	  * 设置：素材ID
	  */
	 public void setMaterialId(String materialId) {
		 this.materialId = materialId;
	 }
	 /**
	  * 获取：素材ID
	  */
	 public String getMaterialId() {
		 return materialId;
	 }
	 /**
	  * 设置：素材名称
	  */
	 public void setMaterialName(String materialName) {
		 this.materialName = materialName;
	 }
	 /**
	  * 获取：素材名称
	  */
	 public String getMaterialName() {
		 return materialName;
	 }
	 /**
	  * 设置：路径
	  */
	 public void setMaterialPath(String materialPath) {
		 this.materialPath = materialPath;
	 }
	 /**
	  * 获取：路径
	  */
	 public String getMaterialPath() {
		 return materialPath;
	 }
	 /**
	  * 设置：相对路径
	  */
	 public void setMaterialRelativePath(String materialRelativePath) {
		 this.materialRelativePath = materialRelativePath;
	 }
	 /**
	  * 获取：相对路径
	  */
	 public String getMaterialRelativePath() {
		 return materialRelativePath;
	 }
	 /**
	  * 设置：标签
	  */
	 public void setMaterialTags(String materialTags) {
		 this.materialTags = materialTags;
	 }
	 /**
	  * 获取：标签
	  */
	 public String getMaterialTags() {
		 return materialTags;
	 }
	 /**
	  * 设置：分组
	  */
	 public void setMaterialGroup(String materialGroup) {
		 this.materialGroup = materialGroup;
	 }
	 /**
	  * 获取：分组
	  */
	 public String getMaterialGroup() {
		 return materialGroup;
	 }
	 /**
	  * 设置：上传时间
	  */
	 public void setUploadTime(Date uploadTime) {
		 this.uploadTime = uploadTime;
	 }
	 /**
	  * 获取：上传时间
	  */
	 public Date getUploadTime() {
		 return uploadTime;
	 }
	 /**
	  * 设置：上传用户
	  */
	 public void setUploadUser(String uploadUser) {
		 this.uploadUser = uploadUser;
	 }
	 /**
	  * 获取：上传用户
	  */
	 public String getUploadUser() {
		 return uploadUser;
	 }
 }
 