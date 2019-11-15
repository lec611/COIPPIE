package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.seu.base.CodeEnum;
import edu.seu.base.CommonResponse;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.Answer;
import edu.seu.service.AnswerService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjx
 * @date 19/10/31
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerService answerService;

    @ResponseBody
    @RequestMapping("/upload")
    public String upload(HttpServletRequest request){
        String type = request.getParameter("type");
        String data = request.getParameter("data");
        double score = 0.0;

        String[] str = data.split("[,\\;]");
        for (String s : str) {
            score += Double.parseDouble(s);
        }
        score /= str.length;

        answerService.uploadAnswer(type,data,score);

        return JSON.toJSONString("success");
    }

    /**
     * function: provide evaluation for evaluation.jsp
     */
    @ResponseBody
    @RequestMapping("/evaluation")
    public void evaluation(HttpServletRequest request){
        String type = request.getParameter("type");

        //如果问卷相应模块已提交
        if(answerService.isSubmitted(type)){
            //生成问卷评估分析报告
        }

    }

    /**
     * function: provide evaluation report download for informationService.jsp
     */
    @ResponseBody
    @RequestMapping("/service")
    public void service(HttpServletRequest request) {
        String user = request.getParameter("user");
        String park = request.getParameter("park");
        String year = request.getParameter("year");
        String invest = request.getParameter("invest");

        try{
            // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
            Configuration configuration = new Configuration(Configuration.getVersion());
            // 第二步：设置模板文件所在的路径。
            configuration.setDirectoryForTemplateLoading(new File("D:\\JetBrains\\MavenWorkspace\\OICPMPIE\\pdfTest"));
            // 第三步：设置模板文件使用的字符集。一般就是utf-8.
            configuration.setDefaultEncoding("utf-8");
            // 第四步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate("hello.ftl");
            // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
            Map dataModel = new HashMap<>();
            //向数据集中添加数据
            dataModel.put("title", "课堂秩序");
            // 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
            Writer out = new FileWriter(new File("D:\\JetBrains\\MavenWorkspace\\OICPMPIE\\pdfTest\\hello.html"));
            // 第七步：调用模板对象的process方法输出文件。
            template.process(dataModel, out);
            // 第八步：关闭流。
            out.close();
        }catch (IOException | TemplateException e){
            LOGGER.error("/answer/service",e);
        }


    }

    @ResponseBody
    @RequestMapping("/query")
    public String query(HttpServletRequest request){
        try {
            String condition = request.getParameter("condition");
            String key = request.getParameter("key");

            List<Answer> answerList = answerService.queryByCondition(condition,key);
            JSONArray array = new JSONArray();
            for (Answer answer : answerList) {
                JSONObject object = new JSONObject();
                object.put("user", answer.getUser());
                object.put("park", answer.getPark());
                object.put("year", answer.getYear());
                object.put("invest", answer.getInvest());
                array.add(object);
            }
            return JSON.toJSONString(array.toString());

        } catch(OICPMPIEExceptions e){
            LOGGER.info(e.getMessage());
            return new CommonResponse(e.getCodeEnum().getValue(),e.getMessage()).toJSONString();
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new CommonResponse(CodeEnum.USER_ERROR.getValue(),e.getMessage()).toJSONString();
        }
    }

}
