package com.tangzhangss.commonutils.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.log.StaticLog;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.*;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.tangzhangss.commonutils.utils.kdn.KdnUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;;
import org.w3c.dom.Document;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;

import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;
import org.xhtmlrenderer.swing.ImageReplacedElement;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1；html转Image
 */
public class HtmlUtil {
    public  static  class ImageConfig{
        //html内容
        public String htmlStr;
        //height
        public int height;
        //wight
        public int width;
        //后缀
        public String suffix="jpg";

        public ImageConfig(String htmlStr, String suffix, int width,int height) {
            this.height = height;
            this.htmlStr = htmlStr;
            this.width = width;
            this.suffix = suffix;
        }
        public ImageConfig(String htmlStr,int width,int height) {
            this.height = height;
            this.htmlStr = htmlStr;
            this.width = width;
        }
    }

    public static void main(String[] args) throws Exception {
        String htmlStr = BaseUtil.readFileContent("/static/pdfTest.html");

//        Pdfcrowd.HtmlToImageClient client =
//                new Pdfcrowd.HtmlToImageClient("tangzhangss", "031e5f005bf5b9950404e886c18f84c6");
//
//        // configure the conversion
//        client.setOutputFormat("jpg");
//
//        client.convertStringToFile(htmlStr,"D:\\static\\test2.jpg");

        String newFilePath="D:\\static\\test.pdf";
        File file = new File(newFilePath);
        if(!file.getParentFile().exists()){
            file.mkdirs();
        }
        file.createNewFile();
              /*
            去除\r\n
         */
        htmlStr = htmlStr.replaceAll("\r|\n*","");
        /*
        特殊字符转换
         */
        htmlStr = htmlStr.replaceAll("&nbsp;","&#160;");
        /*
           快递返回的html 自关闭标签没有结束符号 java2DRenderer解析不了
        */
        htmlStr = KdnUtil.addElementCloseTag(htmlStr).toString();

        htmlStr = KdnUtil.handlePositionAttrAtTdTag(htmlStr).toString();


        System.out.println(htmlToImage(new ImageConfig(htmlStr,"jpg",377,547),"d:\\static\\test.jpg"));
        writeHtmlToPDF(htmlStr,file,new Rectangle(377,547));
    }

    /**
     * HTML转Image
     * @param imageConfig
     * {
     *     宽高建议大于网页内图片的宽高，不然图片显示不完全
     * }
     * @param newFile 文件对象，如果不为null就会将图片写入该文件
     * @return 图片文件base64
     */
    public static String htmlToImage(ImageConfig imageConfig,File newFile) throws Exception {
        // 看代码
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(imageConfig.htmlStr.getBytes(Charset.forName("utf-8"))));
        Java2DRenderer renderer = new Java2DRenderer(document, imageConfig.width, imageConfig.height);

        // 重点在这里，自定义工厂 处理img标签
        renderer.getSharedContext().setReplacedElementFactory(new Base64ImgReplacedElementFactory());
        renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(1);
        BufferedImage image = renderer.getImage();
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            ImageIO.write(image, imageConfig.suffix, outputStream);
            //传入了文件地址写入
            if(newFile!=null){
                ImageIO.write(image,imageConfig.suffix,newFile);
            }
            return Base64Encoder.encode(outputStream.toByteArray());
        }catch (Exception e){
            ExceptionUtil.throwException("HTML to Image fail:"+e.getMessage());
            return null;
        }
    }

    /**
     * 同上文件绝对地址
     * @param newFilePath
     */
    public static String htmlToImage(ImageConfig imageConfig,String newFilePath) throws Exception {
        File file = null;
        if(StringUtils.isNotBlank(newFilePath)){
            file = new File(newFilePath);
            if(!file.getParentFile().exists()){
                file.mkdirs();
            }
            file.createNewFile();
        }
        return htmlToImage(imageConfig,file);
    }

    public static String htmlToImage(ImageConfig imageConfig) throws Exception {
        return htmlToImage(imageConfig,(File)null);
    }

    // 自定义元素工厂的代码
    private static class Base64ImgReplacedElementFactory implements ReplacedElementFactory {

        @SneakyThrows
        @Override
        public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int width, int height) {
            org.w3c.dom.Element e = blockBox.getElement();
            if (e == null) {
                return null;
            }
            String nodeName = e.getNodeName();
            if ("img".equals(nodeName)) {
                // 这里直接从标签获取base64图片的值，如果是地址的话需要通过地址去获取图片
                String attribute = e.getAttribute("src");
                // 这里的width和height就是标签的css属性
                return new ImageReplacedElement(buildImage(attribute, userAgentCallback), width, height);
            }
            return null;
        }

        /**
         * 将base64编码解码并生成图像
         *
         * @param srcAttr 属性
         * @param uac     回调
         * @return FSImage
         * @throws IOException         io异常
         */
        protected java.awt.Image buildImage(String srcAttr, UserAgentCallback uac) throws IOException{

            if (srcAttr.startsWith("data:image/")) {
                String b64encoded = srcAttr.substring(srcAttr.indexOf("base64,") + "base64,".length()
                );
                // 解码
                byte[] decodedBytes = Base64Decoder.decode(b64encoded);
                ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
                return ImageIO.read(bais);
            }
            //http地址下载
            return ImageIO.read(new URL(srcAttr));
        }

        @Override
        public void reset() { }

        @Override
        public void remove(org.w3c.dom.Element e) {

        }

        @Override
        public void setFormSubmissionListener(FormSubmissionListener formSubmissionListener) { }
    }

    /**
     * Html转Pdf
     * @param html html字符串
     * @param file 文件
     * @param rectangle 尺寸 默认A4纸大小
     */
    public static void writeHtmlToPDF(String html, File file,Rectangle rectangle) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(html.getBytes());
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            document.setPageSize(rectangle);
            PdfWriter pdfWriter = PdfWriter.getInstance(document,fileOutputStream);
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);
            final CssFilesImpl cssFiles = new CssFilesImpl();
            cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
            final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
            final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new AsianFontProvider()));
            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
            final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, pdfWriter));
            final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);

            document.open();
            final XMLWorker worker = new XMLWorker(pipeline, true);
            final Charset charset = Charset.forName("UTF-8");
            final XMLParser xmlParser = new XMLParser(true, worker, charset);

            xmlParser.parse(byteArrayInputStream,charset);
//
//            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,
//                    byteArrayInputStream, charset);

            document.close();
        }catch (Exception e){
            ExceptionUtil.throwException("HTML转PDF失败:"+e.getMessage());
        }

    }

    public static void writeHtmlToPDF(String html, File file){
        writeHtmlToPDF(html,file,PageSize.A4);
    }

    /**
     * 用于中文显示的Provider
     */
    private static class AsianFontProvider extends XMLWorkerFontProvider {
        @Override
        public Font getFont(final String fontName, String encoding, float size, final int style) {
            try {
                BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                return new Font(bfChinese, size, style);
            } catch (Exception e) {
            }
            return super.getFont(fontName, encoding, size, style);
        }
    }

    public static class ImageTagProcessor extends com.itextpdf.tool.xml.html.Image {
        @Override
        public List<Element> end(final WorkerContext ctx, final Tag tag, final List<Element> currentContent) {
            final Map<String, String> attributes = tag.getAttributes();
            String src = attributes.get(HTML.Attribute.SRC);
            List<Element> elements = new ArrayList<com.itextpdf.text.Element>(1);
            if (null != src && src.length() > 0) {
                Image img = null;
                if (src.startsWith("data:image/")) {
                    final String base64Data = src.substring(src.indexOf(",") + 1);
                    try {
                        img = Image.getInstance(Base64.decode(base64Data));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (img != null) {
                        try {
                            final HtmlPipelineContext htmlPipelineContext = getHtmlPipelineContext(ctx);
                            elements.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image) getCssAppliers().apply(img, tag, htmlPipelineContext), 0, 0, true), tag,
                                    htmlPipelineContext));
                        } catch (NoCustomContextException e) {
                            throw new RuntimeWorkerException(e);
                        }
                    }
                }

                if (img == null) {
                    elements = super.end(ctx, tag, currentContent);
                }
            }
            return elements;
        }
    }

}
