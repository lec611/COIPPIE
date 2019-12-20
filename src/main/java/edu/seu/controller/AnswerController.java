package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.seu.base.CodeEnum;
import edu.seu.base.CommonResponse;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.Answer;
import edu.seu.service.AnswerService;
import edu.seu.util.Html2PDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
    public void upload(HttpServletRequest request,HttpServletResponse response) {

        String type = (String) request.getSession().getAttribute("type");

        int count=Integer.parseInt(request.getParameter("count"));
        String data = "";

        //如果是规划实施过程问卷的话，则需要对评分进行处理
        if(type.equals("规划实施过程")) {
            String temp;
            for(int i = 0 ;i < 2;i++) {
                temp = request.getParameter("options"+i);
                if(temp == null || temp.equals("")){
                    temp = "0";
                }
                double proportion = Double.parseDouble(temp);
                if(proportion < 50 || proportion > 100){
                    proportion = 0;
                }else if(proportion >= 90 && proportion <= 100){
                    proportion = 100;
                }
                data += proportion+",";
            }
            //对于剩下的回答结果
            for(int i = 2 ;i < count;i++) {
                temp = request.getParameter("options"+i);
                if(temp == null || temp.equals("")){
                    temp = "0";
                }
                data += temp;
                if(i != count-1){
                    data += ",";
                }
            }
        }else{
            //对于剩下来的其他问卷，直接将分数获取并添加在data末尾
            String temp;
            for(int i = 0 ;i < count;i++) {
                temp = request.getParameter("options"+i);
                if(temp == null || temp.equals("")){
                    temp = "0";
                }
                data += temp;
                if(i != count-1){
                    data += ",";
                }
            }
        }

        double score = 0.0;

        String[] str = data.split("[,\\;]");
        for (String s : str) {
            score += Double.parseDouble(s);
        }
        score /= str.length;

        if(type.equals("规划实施环境")){
            type = "environment";
        }else if(type.equals("规划实施过程")){
            type = "process";
        }else if(type.equals("规划实施效果")){
            type = "effect";
        }else if(type.equals("规划工作与成果")){
            type = "result";
        }
        answerService.uploadAnswer(type, data, score);

        try {
            response.sendRedirect(request.getContextPath()+"/informationSurvey.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return JSON.toJSONString("success");
    }

    /**
     * function: provide evaluation for evaluation.jsp
     */
    @ResponseBody
    @RequestMapping("/evaluation")
    public void evaluation(HttpServletRequest request) {
        String type = request.getParameter("type");

        //如果问卷相应模块已提交
        if (answerService.isSubmitted(type)) {
            //生成问卷评估分析报告
        }

    }

    /**
     * function: provide evaluation report download for informationService.jsp
     * @return
     */
    @ResponseBody
    @RequestMapping("/service")
    public String service(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("user");
        String park = request.getParameter("park");
        String year = request.getParameter("year");
        String invest = request.getParameter("invest");

        try {
            //获取本地地址，用于生成PDF文档
            ServletContext context = request.getSession().getServletContext();
            String realPath = context.getRealPath("/ftl");
            //生成PDF
            Html2PDF pdf = new Html2PDF();
            pdf.createPdf(realPath);

            return JSON.toJSONString("success");

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.error("/answer/service", e);
            return null;
        }
    }
    @ResponseBody
    @RequestMapping("/download")
    public void fileDownload(HttpServletRequest request,HttpServletResponse response)
    {
        try{
            ServletContext context = request.getSession().getServletContext();
            String realPath = context.getRealPath("/ftl");
            File file = new File(realPath + "/hello.pdf");
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            ServletOutputStream out = response.getOutputStream();
            FileInputStream ips = new FileInputStream(file);
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/query")
    public String query(HttpServletRequest request) {
        try {
            String condition = request.getParameter("condition");
            String key = request.getParameter("key");

            List<Answer> answerList = answerService.queryByCondition(condition, key);
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

        } catch (OICPMPIEExceptions e) {
            LOGGER.info(e.getMessage());
            return new CommonResponse(e.getCodeEnum().getValue(), e.getMessage()).toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new CommonResponse(CodeEnum.USER_ERROR.getValue(), e.getMessage()).toJSONString();
        }
    }

}
