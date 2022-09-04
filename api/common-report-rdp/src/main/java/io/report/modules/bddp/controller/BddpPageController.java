package io.report.modules.bddp.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileUtil;
import io.report.common.utils.R;
import io.report.common.utils.ServerUtil;
import io.report.modules.bddp.entity.BddpConstant;
import io.report.modules.bddp.util.ZipUtils;

@Controller
@RequestMapping("/bddppage")
public class BddpPageController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${report.bddp.path}")
	private String bddppath;
	@Value("${report.relative-path}")
	private Boolean relativePath;
	@Value("${report.bddp.timeout}")
	private Integer timeout;
	@Autowired
	HttpServletRequest rq;
	@Autowired
	HttpServletResponse rp;

	@RequestMapping("/exportBddp")
	public void exportBddp(String uuid, String bdname, HttpServletResponse response) throws IOException {
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		String folderPath = path + uuid + File.separator;
		byte[] data = ZipUtils.getZipdata(folderPath);
		response.reset();
		response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(bdname) + ".bddp\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}

	@RequestMapping(value = "/importBddp", method = RequestMethod.POST)
	@ResponseBody
	public R importBddp(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.US);
		if(fileType.equals("bddp")){
			String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
			if (path.lastIndexOf(File.separator) == -1) {
				path += File.separator;
			}
			File datafile = FileUtil.file(path + fileName);
			try {
				file.transferTo(datafile);
				// ZipFile zipFile = new ZipFile(datafile);
				// Enumeration<?> entries = zipFile.entries();
				// if (entries.hasMoreElements()) {
				// 	ZipEntry entry = (ZipEntry) entries.nextElement();
				// 	entry.getName();
				// }


				ZipUtils.unZip(datafile, path);
				datafile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
			}
		}else{
			return R.error("文件类型不正确");
		}
		
		return R.ok().put("res", "");
	}

}
