package com.tangzhangss.commonutils.utils;

import cn.hutool.core.io.FileUtil;
import com.lowagie.text.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfUtil {
    public static void pdf2Image(File pdfFile, File outputFile) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        files.add(outputFile);
        pdf2ImageExec(pdfFile,files,96);
    }
    /***
     * PDF文件转图片
     * @param pdfFile pdf文件
     * @param outputFile 输出的文件
     * @param dpi 越大转换后越清晰，相对转换速度越慢,一般电脑默认96dpi
     * 图片File,有几页就有多少图片
     */
    public static void pdf2ImageExec(File pdfFile, List<File> outputFile, Integer dpi) throws IOException {
        if(dpi==null)dpi=96;
        try(PDDocument pdDocument = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            PdfReader reader = new PdfReader(new FileInputStream(pdfFile));
            int pages = reader.getNumberOfPages();// 获取PDF页数
            if(pages>outputFile.size()){
//                ExceptionUtil.throwException("pdf文件的页数为"+pages+"页，输出文件的数量不足");
                //如果页数多了交忽略后面的页
                pages=outputFile.size();
            }
            for (int i = 0; i < pages; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                ImageIO.write(image, FileUtil.getSuffix(outputFile.get(i)),outputFile.get(i));
            }
        }
    }
}
