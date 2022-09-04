package io.report.modules.bddp.controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pro.encryption.entrance.report.loader.RDPCoreContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import io.report.common.utils.Base64Util;
import io.report.common.utils.R;
import io.report.common.utils.ServerUtil;
import io.report.common.utils.sqlite.ResultSetExtractor;
import io.report.common.utils.sqlite.SqliteHelper;
import io.report.modules.bddp.entity.BddpConstant;
import io.report.modules.bddp.entity.SqliteEntity;

/**
 * 大屏幕报表设计器
 * 
 * @author wangcong
 */
@RestController
@RequestMapping("/bddp")
public class BddpController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${report.bddp.path}")
	private String bddppath;
	// @Value("${report.bddp.data-path}")
	// private String bddpFilePath;
	// @Value("${report.bddp.map-path}")
	// private String bddpMapPath;
	// @Value("${report.bddp.bddpfiles-path}")
	private String bddpfilesPath;
	@Value("${report.relative-path}")
	private Boolean relativePath;

	private static final String DefaultConfig = "{\"content\":{\"width\":1920,\"height\":1080,\"globalChartTheme\":\"default\",\"id\":\"fb3cdc463d0bec1550f6a0a739276e05\",\"sceneName\":\"暂存文件\"},\"boxs\":[],\"ruler\":{\"v\":[],\"h\":[]}}";

	/**
	 * 执行SQL -SQLite
	 * 
	 * @param sqlStr
	 * @return 集合
	 */
	@RequestMapping("/executeSQL")
	public R executeSQL(String sqlStr) {
		List<Object> list = new ArrayList<>();
		SqliteEntity res = null;
		System.out.println(sqlStr);
		try {
			SqliteHelper h = new SqliteHelper(getBddpDBPath());
			res = h.executeQuery(sqlStr, new ResultSetExtractor<SqliteEntity>() {
				@Override
				public SqliteEntity extractData(ResultSet rs) {
					SqliteEntity se = new SqliteEntity();
					List<String> columns = new ArrayList<String>();
					List<List<Object>> values = new ArrayList<>();
					try {
						ResultSetMetaData rsmd = rs.getMetaData();
						int colNum = rsmd.getColumnCount();
						for (int i = 1; i <= colNum; i++) {
							columns.add(rsmd.getColumnName(i));
						}
						while (rs.next()) { // 遍历这个数据集
							List<Object> tempList = new ArrayList<>();
							for (int i = 1; i <= colNum; i++) {
								tempList.add(rs.getObject(i));
							}
							values.add(tempList);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					se.setColumns(columns);
					se.setValues(values);
					return se;
				}
			});

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		list.add(res);
		return R.ok().put("res", list);
	}

	/**
	 * 执行SQL -SQLite
	 * 
	 * @param sqlStr
	 * @return 对象
	 */
	@RequestMapping("/executeSQLAsObject")
	public R executeSQLAsObject(String sqlStr) {
		Map<String, Object> obj = null;
		try {
			SqliteHelper h = new SqliteHelper(getBddpDBPath());
			obj = h.executeQuery(sqlStr, new ResultSetExtractor<Map<String, Object>>() {
				@Override
				public Map<String, Object> extractData(ResultSet rs) {
					Map<String, Object> map = new HashMap<>();
					try {
						ResultSetMetaData rsmd = rs.getMetaData();
						int colNum = rsmd.getColumnCount();
						for (int i = 1; i <= colNum; i++) {
							map.put(rsmd.getColumnName(i), rs.getObject(i));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return map;
				}
			});

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return R.ok().put("res", obj);
	}

	/**
	 * 执行SQL -SQLite
	 * 
	 * @param sqlStr
	 * @return 对象
	 */
	@RequestMapping("/executeUpdateSQL")
	public R executeUpdateSQL(@RequestParam(value = "sqlStr[]") String[] sqlStr) {
		try {
			SqliteHelper h = new SqliteHelper(getBddpDBPath());
			h.executeUpdate(sqlStr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return R.ok();
	}

	/**
	 * 执行SQL -SQLite
	 * 
	 * @param sqlStr
	 * @return 对象
	 */
	@RequestMapping("/executeUpdateSQLStr")
	public R executeUpdateSQLStr(@RequestParam(value = "sqlStr") String sqlStr) {
		try {
			SqliteHelper h = new SqliteHelper(getBddpDBPath());
			h.executeUpdate(sqlStr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return R.ok();
	}

	/**
	 * 执行SQL -SQLite
	 * 
	 * @param sqlStr
	 * @return 对象
	 */
	@RequestMapping("/executeInsertSQL")
	public R executeInsertSQL(@RequestParam(value = "sqlStr") String sqlStr) {
		try {
			SqliteHelper h = new SqliteHelper(getBddpDBPath());
			h.executeUpdate(sqlStr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return R.ok();
	}

	/**
	 * 获取大屏幕报表配置文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpFiles")
	public R getBddpFiles() {
		List list = getFilesList(new File(ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH)));
		return R.ok().put("res", list);
	}

	/**
	 * 获取大屏幕报表详细配置信息
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/getJSONFileContent")
	public R getJSONFileContent(String path) {
		FileReader fileReader = new FileReader(path);
		String result = fileReader.readString();
		return R.ok().put("res", result);
	}

	/**
	 * 获取地图数据
	 * 
	 * @param mapName
	 * @return
	 */
	@RequestMapping("/getJSONMapContent")
	public R getJSONMapContent(String mappath) {
		FileReader fileReader = new FileReader(
				ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.MAPFILE_PATH) + mappath);
		String result = fileReader.readString();
		JSONObject jsonObj = JSONObject.parseObject(result);

		return R.ok().put("res", jsonObj);
	}

	/**
	 * 保存大屏幕报表配置文件 旧版的
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping("/saveBddpData")
	public R saveBddpData(String name, String data) {
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		if (path.lastIndexOf(File.separator) == -1) {
			path += File.separator;
		}
		String filepath = path + name + ".json";
		FileWriter writer = new FileWriter(filepath);
		writer.write(data);
		return R.ok();
	}

	/**
	 * 保存预览图
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping("/saveBddpBgi")
	public R saveBddpBgi(String name, String bgi) throws Exception {
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		if (path.lastIndexOf(File.separator) == -1) {
			path += File.separator;
		}
		RDPCoreContext.generateImage(bgi, path + name + "/bgi.png");
		return R.ok();
	}

	/**
	 * 获取大屏幕报表 数据文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpFilesData")
	public R getBddpFilesData() {
		List list = null;
		list = getFilesList(new File(bddpfilesPath + "data"));
		return R.ok().put("res", list);
	}

	/**
	 * 上传数据文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadBddpFilesData", method = RequestMethod.POST)
	@ResponseBody
	public R uploadBddpFilesData(@RequestParam("file") MultipartFile file) {
		// String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		try {
			String path = bddpfilesPath + "data/";
			File datafile = FileUtil.file(path + fileName);
			if (datafile.exists()) {
				return R.error().put("info", "文件已存在！");
			} else {
				file.transferTo(datafile);
				Map<String, String> map = new HashMap<>();
				map.put("name", fileName);
				map.put("path", path + fileName);
				return R.ok().put("res", map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put("res", "");

	}

	/**
	 * 删除大屏幕报表配置文件
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/deleteBddpConfig")
	public R deleteBddpConfig(String path) {
		FileUtil.del(path);
		return R.ok();
	}

	/**
	 * 删除 数据文件
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/deleteBddpFilesData")
	public R deleteBddpFilesData(String path) {
		FileUtil.del(path);
		return R.ok();
	}

	/**
	 * 获取大屏幕报表 图片文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpImgsData")
	public R getBddpImgsData() {
		List list = null;
		list = getFilesList(new File(bddpfilesPath + "images"));
		return R.ok().put("res", list);
	}

	/**
	 * 上传图片文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadBddpImgsData", method = RequestMethod.POST)
	@ResponseBody
	public R uploadBddpImgsData(@RequestParam("file") MultipartFile file) {
		// String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		try {
			String path = bddpfilesPath + "images/";
			File datafile = FileUtil.file(path + fileName);
			if (datafile.exists()) {
				return R.error().put("info", "文件已存在！");
			} else {
				file.transferTo(datafile);
				Map<String, String> map = new HashMap<>();
				map.put("name", fileName);
				map.put("path", path + fileName);
				return R.ok().put("res", map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put("res", "");

	}

	/**
	 * 删除 数据文件
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/deleteBddpImgsData")
	public R deleteBddpImgsData(String path) {
		FileUtil.del(path);
		return R.ok();
	}

	/**
	 * 获取当前文件夹下的所有文件，不递归
	 * 
	 * @param file
	 * @return
	 */
	public static List<Map<String, String>> getFilesList(File file) {
		List<Map<String, String>> list = new ArrayList<>();
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				Map<String, String> map = new HashMap<>();
				map.put("name", files[i].getName());
				map.put("path", files[i].getPath());
				list.add(map);
			} else {
				// getFilesList(files[i]);
			}
		}
		return list;
	}
	// 新的保存方式

	/**
	 * 获取大屏幕报表配置文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpFolders")
	public R getBddpFolders() {
		List list = getFoldersList(
				new File(ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH)));
		return R.ok().put("res", list);
	}

	/**
	 * 获取大屏幕报表详细配置信息
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/getJSONFoldersContent")
	public R getJSONFoldersContent(String path) {
		FileReader fileReader = new FileReader(path + File.separator + "config.json");
		String result = fileReader.readString();
		return R.ok().put("res", result);
	}

	/**
	 * 获取大屏幕报表详细配置信息
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/getJSONFoldersContentById")
	public R getJSONFoldersContentById(String id) {
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		FileReader fileReader = new FileReader(path + id + File.separator + "config.json");
		String result = fileReader.readString();
		return R.ok().put("res", result);
	}

	/**
	 * 获取大屏幕报表详细配置信息并复制到当前的大屏幕中
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/getJSONFoldersContentForCopy")
	public R getJSONFoldersContentForCopy(String path, String sourceId, String id) {
		FileReader fileReader = new FileReader(path + File.separator + "config.json");

		String destpath = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		FileUtil.copy(new File(path + File.separator + "data"), new File(destpath + id), true);
		FileUtil.copy(new File(path + File.separator + "images"), new File(destpath + id), true);
		String result = fileReader.readString();
		result = result.replaceAll(sourceId, id);
		return R.ok().put("res", result);
	}

	/**
	 * 保存大屏幕报表配置文件
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping("/saveBddpDataForFolder")
	public R saveBddpDataForFolder(String name, String data) {
		try {
			data = Base64Util.decode(data, "Unicode");
			// System.out.println(data);
			data = data.replaceAll("&amp;", "&");
			String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
			String folderPath = path + name + File.separator;
			if (!FileUtil.exist(folderPath)) {
				FileUtil.mkdir(folderPath);
			}
			FileWriter writer = new FileWriter(folderPath + "config.json");
			writer.write(data);
			return R.ok();
		} catch (Exception e) {
			return R.error("保存失败");
		}

	}

	/**
	 * 获取大屏幕报表 图片文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpFolderImgsData")
	public R getBddpFolderImgsData(String id) {
		List list = null;
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		File file = new File(path + id + File.separator + "images");
		if (!FileUtil.exist(file)) {
			FileUtil.mkdir(file);
		}
		list = getFilesList(file);
		return R.ok().put("res", list);
	}

	/**
	 * 上传图片文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadBddpFolderImgsData", method = RequestMethod.POST)
	@ResponseBody
	public R uploadBddpFolderImgsData(@RequestParam("file") MultipartFile file, String bddpId) {
		// String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		try {
			String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
			if (path.lastIndexOf(File.separator) == -1) {
				path += File.separator;
			}

			String filepath = path + bddpId + File.separator + "images/";
			File datafile = FileUtil.file(filepath + fileName);
			if (datafile.exists()) {
				return R.error().put("info", "文件已存在！");
			} else {
				file.transferTo(datafile);
				Map<String, String> map = new HashMap<>();
				map.put("name", fileName);
				map.put("path", filepath + fileName);
				return R.ok().put("res", map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put("res", "");

	}

	/**
	 * 获取大屏幕报表 数据文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpFolderFilesData")
	public R getBddpFolderFilesData(String id) {
		List list = null;
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		if (path.lastIndexOf(File.separator) == -1) {
			path += File.separator;
		}
		File file = new File(path + id + File.separator + "data");
		if (!FileUtil.exist(file)) {
			FileUtil.mkdir(file);
		}
		list = getFilesList(file);
		return R.ok().put("res", list);
	}

	/**
	 * 获取大屏幕报表 地图文件
	 * 
	 * @return
	 */
	@RequestMapping("/getBddpMapFilesTree")
	public R getBddpMapFilesTree() {
		JSONArray jsonArray = new JSONArray();
		getFilesTree(new File(ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.MAPFILE_PATH)), "0",
				jsonArray);
		JSONObject json = new JSONObject();
		json.put("id", "0");
		json.put("name", "地图");
		json.put("click", false);
		jsonArray.add(json);
		return R.ok().put("res", jsonArray);
	}

	/**
	 * 上传数据文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadBddpFolderFilesData", method = RequestMethod.POST)
	@ResponseBody
	public R uploadBddpFolderFilesData(@RequestParam("file") MultipartFile file, String bddpId) {
		// String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		try {
			String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
			if (path.lastIndexOf(File.separator) == -1) {
				path += File.separator;
			}
			String filepath = path + bddpId + File.separator + "data/";
			File datafile = FileUtil.file(filepath + fileName);
			if (datafile.exists()) {
				return R.error().put("info", "文件已存在！");
			} else {
				file.transferTo(datafile);
				Map<String, String> map = new HashMap<>();
				map.put("name", fileName);
				map.put("path", filepath + fileName);
				return R.ok().put("res", map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put("res", "");

	}

	/**
	 * 保存自定义组件
	 * 
	 * @return
	 */
	@RequestMapping("/saveDiyTags")
	public R saveDiyTags(String id, String formId, String data) {
		String path = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.DIYTAGS_PATH);
		String folderPath = path + id + File.separator;
		if (!FileUtil.exist(folderPath)) {
			FileUtil.mkdir(folderPath);
		}
		copyImageAndDataForDiyTags(id, formId, data);
		data = data.replaceAll(BddpConstant.BDDPCONFIG_V_PATH, BddpConstant.DIYTAGS_V_PATH);
		data = data.replaceAll(formId, id);
		FileWriter writer = new FileWriter(folderPath + "config.json");
		writer.write(data);
		return R.ok();
	}

	/**
	 * 获取自定义组件
	 * 
	 * @return
	 */
	@RequestMapping("/getDiyTags")
	public R getDiyTags() {

		File file = new File(ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.DIYTAGS_PATH));
		if (!FileUtil.exist(file)) {
			FileUtil.mkdir(file);
		}
		List list = getDiyTagFoldersList(file);
		return R.ok().put("res", list);
	}


	public void copyImageAndDataForDiyTags(String id, String fromId, String jsonStr) {
		String bddpPath = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.BDDPFILE_PATH);
		String diyTagsPath = ServerUtil.getDataPath(relativePath, bddppath, BddpConstant.DIYTAGS_PATH);
		JSONObject json = JSONObject.parseObject(jsonStr);
		JSONArray array = json.getJSONArray("boxs");
		for (int i = 0; i < array.size(); i++) {
			JSONObject box = array.getJSONObject(i);
			if (box.containsKey("parts")) {
				JSONObject parts = box.getJSONObject("parts");
				if (parts.containsKey("backgroundImage")) {
					String backgroundImage = parts.getString("backgroundImage");
					if (backgroundImage.indexOf(fromId) > -1) {
						String[] paths = backgroundImage.split(fromId);
						FileUtil.copy(new File(bddpPath + fromId + File.separator + paths[1]),
								new File(diyTagsPath + id + File.separator + paths[1]), true);
					}
				}
				if (parts.containsKey("imgUrl")) {
					String imgUrl = parts.getString("imgUrl");
					if (imgUrl.indexOf(fromId) > -1) {
						String[] paths = imgUrl.split(fromId);
						FileUtil.copy(new File(bddpPath + fromId + File.separator + paths[1]),
								new File(diyTagsPath + id + File.separator + paths[1]), true);
					}
				}
			}
			if (box.containsKey("data")) {
				JSONObject data = box.getJSONObject("data");
				if (data.containsKey("link")) {
					String link = data.getString("link");
					if (link.indexOf(fromId) > -1) {
						String[] paths = link.split(fromId);
						FileUtil.copy(new File(bddpPath + fromId + File.separator + paths[1]),
								new File(diyTagsPath + id + File.separator + paths[1]), true);
					}
				}
			}
			// System.out.println(box.getString("type"));
		}
	}

	/**
	 * 获取当前文件夹下的所有文件夹，不递归
	 * 
	 * @param file
	 * @return
	 */
	public static List<Map<String, String>> getFoldersList(File file) {
		List<Map<String, String>> list = new ArrayList<>();
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				Map<String, String> map = new HashMap<>();
				String configPath = files[i].getPath() + File.separator + "config.json";
				File datafile = FileUtil.file(configPath);
				if (datafile.exists()) {
					FileReader fileReader = new FileReader(configPath);
					String result = fileReader.readString();
					JSONObject json = JSONObject.parseObject(result);
					String bdname = json.getJSONObject("content").getString("sceneName");
					map.put("bdname", bdname);
				} else {
					JSONObject json = JSONObject.parseObject(DefaultConfig);
					json.getJSONObject("content").put("id", files[i].getName());
					json.getJSONObject("content").put("sceneName", "暂存" + files[i].getName());
					FileWriter writer = new FileWriter(configPath);
					writer.write(json.toString());
					map.put("bdname", "暂存" + files[i].getName());
				}
				map.put("name", files[i].getName());
				map.put("path", files[i].getPath());
				map.put("time", files[i].lastModified() + "");
				list.add(map);
			} else {
				// getFilesList(files[i]);
			}
		}
		return list;
	}

	public static List<Map<String, String>> getDiyTagFoldersList(File file) {
		List<Map<String, String>> list = new ArrayList<>();
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				Map<String, String> map = new HashMap<>();
				String configPath = files[i].getPath() + File.separator + "config.json";
				File datafile = FileUtil.file(configPath);
				if (datafile.exists()) {
					FileReader fileReader = new FileReader(configPath);
					String result = fileReader.readString();
					JSONObject json = JSONObject.parseObject(result);
					map.put("boxs", json.getJSONArray("boxs").toString());
					map.put("icon", json.getString("icon"));
					map.put("name", json.getString("name"));
					map.put("id", json.getString("id"));
					list.add(map);
				}
			} else {
				// getFilesList(files[i]);
			}
		}
		return list;
	}

	public static void getFilesTree(File file, String index, JSONArray zNodes) {
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			JSONObject json = new JSONObject();
			json.put("pId", index);
			json.put("id", index + "-" + i);
			json.put("name", files[i].getName());
			json.put("path", files[i].getPath());
			zNodes.add(json);
			if (files[i].isFile()) {
				json.put("click", true);
			} else {
				json.put("click", false);
				getFilesTree(files[i], index + "-" + i, zNodes);
			}
		}
	}

	public String getBddpfilesPath() {
		return bddpfilesPath;
	}

	public void setBddpfilesPath(String bddpfilesPath) {
		this.bddpfilesPath = bddpfilesPath;
	}

	public static String getBddpDBPath() {
		String path = "";
		try {
			Resource resource = new ClassPathResource("bddp.db");
			path = resource.getURL().getPath();
			if (getCount(path, ".jar") > 0) {
				path = System.getProperty("user.dir") + File.separator + "bddp.db";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//path = "G:\\workspace\\githubproject\\mftcc-rdp-2.4.5\\RDPServer\\report-admin\\src\\main\\resources\\bddp.db";
		return path;
	}

	public static int getCount(String str, String key) {
		if (str == null || key == null || "".equals(str.trim()) || "".equals(key.trim())) {
			return 0;
		}
		int count = 0;
		int index = 0;
		while ((index = str.indexOf(key, index)) != -1) {
			index = index + key.length();
			count++;
		}
		return count;
	}
}
