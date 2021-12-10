package com.tangzhangss.commonutils.utils;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.json.JSONObject;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtil {


    /**
     * 下载（克隆）inputStream

     */
    public static InputStream cloneInputStream(InputStream input) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return new ByteArrayInputStream(baos.toByteArray());
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 判断两个文件是否相同___主要判断不同文件
     * 注意::网络文件流需要确保下载完成
     * @param in1 需要检验的文件流
     * @param in2 数据库拿出的文件流
     * @return
     */
    public static Boolean isSameFile(InputStream in1 , InputStream in2) throws IOException {


        int len = in1.available();//返回总的字节数
        int len2 = in2.available();

        if (len!=len2) {//长度不相等
            return false;
        }

        byte buffer[] = new byte[len];
        byte buffer2[] = new byte[len2];

        in1.read(buffer);
        in2.read(buffer2);

        for (int i=0;i<len;i++) {
            if(buffer[i]!=buffer2[i]) {
                return false;
            }
        }

        return true;
    }
    /**
     * 获取文件的md5值
     * md5相同也不能保证不是同一个文件
     * @param in 文件流
     * @return 返回文件的md5值字符串
     */
    public static String getFileMD5(InputStream in) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = null;
        byte buffer[] = new byte[8192];
        int len;
        digest = MessageDigest.getInstance("MD5");
        while ((len = in.read(buffer)) != -1) {
            digest.update(buffer, 0, len);
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }

    /**
     * 解析Excel文件
     * 检验解析文件的结果
     * 第一行字段名必须以 i s n d t b开头 表示字段类型 iPassword=password表示字段英文名字
     * 第二行字段名字 后面 * 表示必填
     * 后面的是数据行
     * sId  sName
     * 主键*  名字
     * 表示字段都是String类型的。主键必填
     */
    public static List<List<Object>> analysisExcelVerifiable(File excelFile) throws Exception {
        try(FileInputStream fileInputStream = new FileInputStream(excelFile)){
            Workbook wb = null;
            //        Workbook wb = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(
//                sType)?new XSSFWorkbook(new FileInputStream(tempFile)):new HSSFWorkbook(new FileInputStream(tempFile));
            if (isExcel2007(excelFile.getName())) {
                wb = new XSSFWorkbook(fileInputStream);
            } else {
                wb = new HSSFWorkbook(fileInputStream);
            }
            List<List<Object>> list = new LinkedList<List<Object>>();
            // 读取第一张表格内容
            Sheet sheet = wb.getSheetAt(0);
            Row row = null;
            Cell cell = null;
            for (int i = 0; i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {
                row = sheet.getRow(i);
                if (row == null){
                    continue;
                }
                List<Object> linked = new LinkedList<Object>();
                //行的解析状态
                Integer[] iStatus= new Integer[1];//状态0:该行正常5:该行警告信息/10:该行错误校验不通过
                iStatus[0]=0;
                StringBuffer sMessage= new StringBuffer();//错误信息

                //row.getLastCellNum()是获取最后一个不为空的列是第几个
                //直接用第一行的列数作为标准
                for (int j = 0; j <= sheet.getRow(0).getLastCellNum()-1; j++) {
                    Object value = null;
                    cell = row.getCell(j);
                    if (cell == null) {
                        linked.add(null);//每一个单元格都一一对应
                        if(sheet.getRow(1).getCell(j).getStringCellValue().endsWith("*")){
                            iStatus[0] = 10;
                            sMessage.append(sheet.getRow(1).getCell(j)+"不能为空;");
                        }
                        continue;
                    }
                    CellType cellType = cell.getCellType();
                    //如果是函数的话 就会走这里把计算结果算出来
                    if (cell.getCellType() == CellType.FORMULA) {
                        cellType = cell.getCachedFormulaResultType();
                    }
                    value = getCellValue(cell,cellType,sheet,i,j,iStatus,sMessage);

                    linked.add(value);
                }
//            System.out.println(linked);
                if (linked.size()!= 0) {
                    //添加解析状态
                    if(i==1){
                        linked.add("解析状态");
                        linked.add("错误/警告");
                    }else if(i==0){
                        linked.add("iStatus");
                        linked.add("sMessage");
                    }else{
                        linked.add(iStatus[0]);
                        linked.add(sMessage);
                    }
                    list.add(linked);
                }
            }
            return list;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 基本解析excel
     * @param excelFile 文件
     * @return 解析之后的list
     * @throws Exception
     */
    public static List<List<Object>> analysisExcel(File excelFile) throws Exception {
        try(FileInputStream fileInputStream = new FileInputStream(excelFile)){
            Workbook wb = null;
            //        Workbook wb = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(
//                sType)?new XSSFWorkbook(new FileInputStream(tempFile)):new HSSFWorkbook(new FileInputStream(tempFile));
            if (isExcel2007(excelFile.getName())) {
                wb = new XSSFWorkbook(fileInputStream);
            } else {
                wb = new HSSFWorkbook(fileInputStream);
            }
            List<List<Object>> list = new LinkedList<List<Object>>();
            // 读取第一张表格内容
            Sheet sheet = wb.getSheetAt(0);
            Row row = null;
            Cell cell = null;
            for (int i = 0; i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                List<Object> linked = new LinkedList<Object>();

                //row.getLastCellNum()是获取最后一个不为空的列是第几个
                //直接用第一行的列数作为标准
                for (int j = 0; j <= sheet.getRow(0).getLastCellNum() - 1; j++) {
                    Object value = null;
                    cell = row.getCell(j);
                    if (cell == null) {
                        linked.add(null);//每一个单元格都一一对应
                        continue;
                    }
                    CellType cellType = cell.getCellType();
                    //如果是函数的话 就会走这里把计算结果算出来
                    if (cell.getCellType() == CellType.FORMULA) {
                        cellType = cell.getCachedFormulaResultType();
                    }
                    value = getCellValue(cell, cellType, sheet, i, j);

                    linked.add(value);
                }
                if (linked.size()!= 0) {
                    list.add(linked);
                }
            }
            return list;
        }catch (Exception e){
            throw e;
        }

    }
    /**
     * 计算单元格的值
     * @param cell 单元格
     * @param cellType 单元格类型
     * @param sheet
     * @param i 行
     * @param j 列
     * @param iStatus 状态 0 没问题 5 警告 10错误（如必填的没有填，*号）
     * @param sMessage 提示信息
     * @return
     */
    private static Object getCellValue(Cell cell,CellType cellType,Sheet sheet,int i,int j,Integer[] iStatus, StringBuffer sMessage){
        Object value = null;
        switch (cellType) {
            case STRING:
                //if当前列不是string类型的出现警告信息
                if(i>1) checkCellFormat("String",sheet,j,iStatus,sMessage);

                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                //日期数据返回LONG类型的时间戳
                if (DateUtil.isCellDateFormatted(cell)) {
                    if("DateTime".equals(getSheetCellType(sheet,j))){
                        if(i>1) checkCellFormat("DateTime",sheet,j,iStatus,sMessage);
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                    }else{
                        if(i>1) checkCellFormat("Date",sheet,j,iStatus,sMessage);
                        value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    }
                } else {
                    //数值类型返回double类型的数字
                    if(i>1) checkCellFormat("Number",sheet,j,iStatus,sMessage);
                    if("i".equals(getColumnTypePrefix(sheet,j))){
                        value = (int)cell.getNumericCellValue();
                    }else{
                        //默认double类型
                        value = cell.getNumericCellValue();
                    }

                    //如果value是xx.00这种格式的
                    //能转成int的就转成int
                    int vInt = (int)cell.getNumericCellValue();
                    if(CompareUtil.compare(Double.valueOf(vInt),value,false)==0)value=vInt;

                }
                break;
            case BOOLEAN:
                //布尔类型
                if(i>1) checkCellFormat("Boolean",sheet,j,iStatus,sMessage);
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                //空单元格
                if(sheet.getRow(1).getCell(j).getStringCellValue().endsWith("*")){
                    iStatus[0] = 10;
                    sMessage.append(sheet.getRow(1).getCell(j)+"不能为空;");
                }
                value=null;
                break;
            default:
                value = cell.toString();
        }
        return value;
    }

    /**
     * 计算单元格的值 不带解析校验
     */
    private static Object getCellValue(Cell cell,CellType cellType,Sheet sheet,int i,int j){
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                //日期数据返回LONG类型的时间戳
                if (DateUtil.isCellDateFormatted(cell)) {
                    if("DateTime".equals(getSheetCellType(sheet,j))){
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                    }else{
                        value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    }
                } else {
                    //数值类型返回double类型的数字
                    if("i".equals(getColumnTypePrefix(sheet,j))){
                        value = (int)cell.getNumericCellValue();
                    }else{
                        //默认double类型
                        value = cell.getNumericCellValue();
                    }
                    //如果value是xx.00这种格式的
                    //能转成int的就转成int
                    int vInt = (int)cell.getNumericCellValue();
                    if(CompareUtil.compare(Double.valueOf(vInt),value,false)==0)value=vInt;

                }
                break;
            case BOOLEAN:
                //布尔类型
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                //空单元格
                value=null;
                break;
            default:
                value = cell.toString();
        }
        return value;
    }


    /**
     * multipartFile 对象转 file对象
     */
    public static File multipartFileTransferToFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile==null)return null;
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        String originalFilename = multipartFile.getOriginalFilename();
        String prefix=originalFilename.substring(0,originalFilename.lastIndexOf("."));
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        file=File.createTempFile(prefix,suffix);
        multipartFile.transferTo(file);
        file.deleteOnExit();
        return file;
    }

    /**
     * url路径 生成 转file对象
     */
    public static File urlPathTransferToFile(String urlPath) throws IOException {
        if(StringUtils.isBlank(urlPath))return null;

        URL url = new URL(urlPath);
        URLConnection urlConn = url.openConnection();
        InputStream input = FileUtil.cloneInputStream(urlConn.getInputStream());

//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        String prefix=urlPath.substring(urlPath.lastIndexOf("/")+1,urlPath.lastIndexOf("."));
        String suffix=urlPath.substring(urlPath.lastIndexOf("."));
        file=File.createTempFile(prefix,suffix);
        //写入文件
        cn.hutool.core.io.FileUtil.writeFromStream(input,file);
        file.deleteOnExit();
        return file;
    }


    private static void checkCellFormat(String cType, Sheet sheet, int j, Integer[] iStatus, StringBuffer sMessage){
        //需要的格式
        String sctype = getSheetCellType(sheet,j);
        //如果单元格需要int类型excel解析式是String
        if(sctype.equals("Number")&&cType.equals("String")){
            return;
        }

        //String类型的都可以填
        if(!sctype.equals("String")&&!cType.equals(sctype)){
            iStatus[0] = iStatus[0]>5?iStatus[0]:5;
            sMessage.append(sheet.getRow(1).getCell(j).toString()+"格式错误，需要:"+sctype+";");
        }
    }
    //获取列的类型前缀
    private static String getColumnTypePrefix(Sheet sheet,int j){
        String col=sheet.getRow(0).getCell(j).toString();
        char symbol = col.charAt(0);
        return String.valueOf(symbol);
    }
    private static String getSheetCellType(Sheet sheet,int j){
        String col=sheet.getRow(0).getCell(j).toString();
        char symbol = col.charAt(0);
//        System.out.println(col+"_"+symbol);
        switch (symbol){
            case 'u':
            case 's':return "String";
            case 'd':return "Date";
            case 't':return "DateTime";
            case 'i':
            case 'n':return "Number";
            case 'b':return "Boolean";
        }
        return "String";
    }

    /*
     *将list<list<>>格式的excel数据格式转成 list<map<>>
     */
    public static List<Map<Object, Object>> getJsonObject(List<List<Object>> list){
        if(null == list){
            return null;
        }
        if (list.size()<2){
            return new ArrayList<>(1);
        }
        List<Map<Object,Object>> result = new LinkedList<>();
        for (int i=2;i<list.size();i++){
            Map<Object,Object> rowMap = new HashMap<>();
            for (int j=0;j<list.get(i).size();j++){
                rowMap.put(list.get(0).get(j),list.get(i).get(j));
            }
            result.add(rowMap);
        }
        return result;
    }

    public static JSONObject getExcelHeaderData(List<List<Object>> list) {
        JSONObject data = new JSONObject();
        int size = list.get(0).size()-2;
        for (int j=0;j<size;j++){
            data.set(String.valueOf(list.get(0).get(j)),list.get(1).get(j));
        }
        return data;
    }

    /**
     * 创建文件
     * @param filePath 文件路经
     * @return
     * @throws IOException
     */
    public File createFile(String filePath) throws IOException {
        File newFile = new File(filePath);
        if(newFile.exists()){
            newFile.delete();
        }
        //创建目录
        File fileParent = newFile.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        newFile.createNewFile();
        return newFile;
    }

}
