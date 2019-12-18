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
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
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
    public String upload(HttpServletRequest request) {
        String type = request.getParameter("type");
        String data = request.getParameter("data");
        double score = 0.0;

        String[] str = data.split("[,\\;]");
        for (String s : str) {
            score += Double.parseDouble(s);
        }
        score /= str.length;

        answerService.uploadAnswer(type, data, score);

        return JSON.toJSONString("success");
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
    public ResponseEntity<byte[]> service(HttpServletRequest request) {
        String user = request.getParameter("user");
        String park = request.getParameter("park");
        String year = request.getParameter("year");
        String invest = request.getParameter("invest");

        try {

            String realPath = "D:\\JetBrains\\MavenWorkspace\\OICPMPIE\\src\\main\\webapp\\WEB-INF\\ftl";
            Html2PDF pdf = new Html2PDF();
            realPath = pdf.createPdf(realPath);

            System.out.println("1:"+realPath);

            File file = new File(realPath);
            System.out.println(file.getName());

            //return JSON.toJSONString("success");

            //File file = new File(realPath);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDispositionFormData("attachment", URLEncoder.encode(file.getName(), "UTF-8"));

            System.out.println("2:"+realPath);

            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),httpHeaders, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.error("/answer/service", e);
            return null;
            //return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
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
