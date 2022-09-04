package io.report.modules.material.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;
import io.report.common.utils.ServerUtil;
import io.report.common.validator.ValidatorUtils;
import io.report.modules.material.entity.MaterialEntity;
import io.report.modules.material.service.MaterialService;
import io.report.modules.sys.entity.SysUserEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 09:08:35
 */
@RestController
@RequestMapping("material/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Value("${report.material.path}")
    private String materialpath;
    @Value("${report.relative-path}")
    private Boolean relativePath;

    // 文件上传相关代码
    @RequestMapping(value = "upload")
    @ResponseBody
    public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return R.error().put("code", 0).put("msg", "文件为空!");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = ServerUtil.getDataPath(relativePath, materialpath);
        String uuid = RandomUtil.randomUUID();
        // 解决中文问题，liunx下中文路径，图片显示问题
        String path = filePath + uuid + suffixName;
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            MaterialEntity material = new MaterialEntity();
            String group = request.getParameter("group");
            if (group == null || "".equals(group)) {
                group = "默认";
            }
            String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
            Date uploadTime = DateUtil.date();
            material.setMaterialId(uuid);
            material.setMaterialGroup(group);
            material.setMaterialName(fileName);
            material.setMaterialPath(Base64.encode(path));
            material.setMaterialRelativePath("material/" + uuid + suffixName);
            material.setUploadUser(username);
            material.setUploadTime(uploadTime);
            materialService.insert(material);
            return R.ok().put("code", 0).put("msg", "上传成功!");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error().put("code", 0).put("msg", "失败成功!");
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("material:material:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = materialService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取分类
     */
    @RequestMapping("/groups")
    public R groups() {
        EntityWrapper<MaterialEntity> wrapper = new EntityWrapper<MaterialEntity>();
        wrapper.groupBy("material_group");
        List<MaterialEntity> list = materialService.selectList(wrapper);
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{materialId}")
    @RequiresPermissions("material:material:info")
    public R info(@PathVariable("materialId") String materialId) {
        MaterialEntity material = materialService.selectById(materialId);

        return R.ok().put("material", material);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("material:material:save")
    public R save(@RequestBody MaterialEntity material) {
        materialService.insert(material);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("material:material:update")
    public R update(@RequestBody MaterialEntity material) {
        ValidatorUtils.validateEntity(material);
        materialService.updateAllColumnById(material);// 全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("material:material:delete")
    public R delete(@RequestParam("materialId") String materialId) {
        if(materialId !=null &&!"".equals(materialId)){
            EntityWrapper<MaterialEntity> ew = new EntityWrapper<MaterialEntity>();
            ew.where("material_id  = {0}", materialId);
            MaterialEntity material = materialService.selectOne(ew);
            materialService.delete(ew);
            String filePath = ServerUtil.getDataPath(relativePath, materialpath);
            String suffix = material.getMaterialRelativePath().substring(material.getMaterialRelativePath().lastIndexOf(".") );
            String filename = filePath+materialId+suffix;
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }

        }
        return R.ok();
    }

}
