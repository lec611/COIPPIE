package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.seu.base.CodeEnum;
import edu.seu.base.CommonResponse;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.Answer;
import edu.seu.model.Questionnaire;
import edu.seu.service.AnswerService;
import edu.seu.service.QuestionnaireService;
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
import java.util.Arrays;
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

    @Autowired
    private QuestionnaireService questionnaireService;

    @ResponseBody
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request,HttpServletResponse response) {

        String type = (String) request.getSession().getAttribute("type");

        int count=Integer.parseInt(request.getParameter("count"));
        String data = "";

        //如果是规划实施过程问卷的话，则需要对评分进行处理test
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
     * function: provide evaluationScore for evaluation.jsp
     */
    @ResponseBody
    @RequestMapping("/evaluation")
    public String evaluation(HttpServletRequest request) {
        String type = request.getParameter("type");
        Map<String,String> questionMapping = new HashMap();
        questionMapping.put("environment","规划实施环境");
        questionMapping.put("process","规划实施过程");
        questionMapping.put("effect","规划实施效果");
        questionMapping.put("result","规划工作与成果");
        //questionMapping.put("total","总体评估报告");
        //如果问卷相应模块已提交
        if (answerService.isSubmitted(type)) {
            //返回相应问卷结果字串供前端生成图表
            if("total".equals(type)){
                String total = answerService.answerScore(type);
                String[] totalScore = total.split(",");
//                for(int i = 0 ; i < totalScore.length;i++)
//                {
//                    totalScore[i] = totalScore[i].substring(0,5);
//                }
                //System.out.println(toString(type, Arrays.asList(totalScore),null));
                return JSON.toJSONString(toString(type, Arrays.asList(totalScore),null));
            }else{
                List<String> question = questionnaireService.showQuestion(questionMapping.get(type));
                String score = answerService.answerScore(type);
                System.out.println(toString(type,question,score));
                return JSON.toJSONString(toString(type,question,score));
            }
        }else{
            return JSON.toJSONString("未提交相应问卷");
        }
    }

    public String toString(String type,List<String> question,String score){
        String data;
        if(type.equals("total")){
            data="{\"subScore\":[";
            for(int i=0;i<4;i++){
                data += question.get(i);
                if(i != 3){
                    data += ",";
                }
            }
            double totalScore = Double.parseDouble(question.get(4));
            data += "],\"totalScore\":["+totalScore+"],\"level\":[\"";
            if(totalScore >= 80){
                data += "优秀";
            }else if(totalScore<80 && totalScore>=70){
                data += "良好";
            }else if(totalScore<70 && totalScore>=60){
                data += "一般";
            }else if(totalScore<60 && totalScore>=40){
                data += "较差";
            }else if(totalScore<40){
                data += "很差";
            }
            data += "\"]}";
        }else{
            data="{\"question\":[\"";
            System.out.println("size:"+question.size());
            for(int i=0;i<question.size();i++){
                data += question.get(i);
                if(i != question.size()-1){
                    data += "\",\"";
                }
            }
            data += "\"],\"score\":["+score+"]}";
        }
        return data;

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
        String totalScore = answerService.selectByThreeElement(user,park,year,invest);
        try {
            //获取本地地址，用于生成PDF文档
            ServletContext context = request.getSession().getServletContext();
            String realPath = context.getRealPath("/ftl");
            //生成PDF
            Html2PDF pdf = new Html2PDF();
            pdf.createPdf(realPath,user,park,year,invest,totalScore);

            return JSON.toJSONString("下载成功");

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
            /*
             File file = new File(realPath + "/hello.html");
            response.setContentType("application/form-data");
             */
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
