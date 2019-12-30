package edu.seu.util;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjx
 * @date 2019/11/20
 */
public class Html2PDF {
    public String createPdf(String realPath,String user,String park,String year,String invest,String totalScore) throws Exception {
        //根据ftl模板生成html文件
        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对应的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File(realPath));
        // 第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        // 第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("hello.ftl");
        // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map<String,String> dataModel = new HashMap();
        //处理分数
        String[] result = totalScore.split(",");
        double totalResult = Double.parseDouble(result[4]);
        String level = "";
        if(totalResult >= 80){
            level = "优秀";
        }else if(totalResult<80 && totalResult>=70){
            level = "良好";
        }else if(totalResult<70 && totalResult>=60){
            level = "一般";
        }else if(totalResult<60 && totalResult>=40){
            level = "较差";
        }else if(totalResult<40){
            level = "很差";
        }

        //向数据集中添加数据
        dataModel.put("title", "总得分情况");
        dataModel.put("year",year);
        dataModel.put("park",park);
        dataModel.put("totalScore",result[4]);
        dataModel.put("level",level);
        dataModel.put("enScore",result[0]);
        dataModel.put("prScore",result[1]);
        dataModel.put("efScore",result[2]);
        dataModel.put("reScore",result[3]);
        // 第六步：创建一个Writer对象，解决静态页面中文乱码问题，指定生成的文件名。
        File htmlFile = new File(realPath+"/hello.html");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
        // 第七步：调用模板对象的process方法输出文件。
        template.process(dataModel, out);
        // 第八步：关闭流。
        out.close();

        //将生成的html文件转换成PDF文件
        String inputFile = realPath+"/hello.html";
        String outputFile = realPath+"/hello.pdf";
        String url = new File(inputFile).toURI().toURL().toString();

        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        //解决中文支持
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("c:/Windows/Fonts/Simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        renderer.layout();
        renderer.createPDF(os);
        os.close();
        return outputFile;
    }
}
