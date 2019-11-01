package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.seu.base.CodeEnum;
import edu.seu.base.CommonResponse;
import edu.seu.exceptions.COIPPIEExceptions;
import edu.seu.model.Answer;
import edu.seu.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @ResponseBody
    @RequestMapping("/evaluation")
    public void evaluation(HttpServletRequest request){
        String type = request.getParameter("type");

        //如果问卷相应模块已提交
        if(answerService.isSubmitted(type)){
            //生成问卷评估分析报告
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
            for(int i = 0;i < answerList.size();i++){
                JSONObject object = new JSONObject();
                object.put("park", answerList.get(i).getPark());
                object.put("year", answerList.get(i).getYear());
                object.put("invest", answerList.get(i).getInvest());
                array.add(object);
            }
            return JSON.toJSONString(array.toString());

        } catch(COIPPIEExceptions e){
            LOGGER.info(e.getMessage());
            return new CommonResponse(e.getCodeEnum().getValue(),e.getMessage()).toJSONString();
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new CommonResponse(CodeEnum.USER_ERROR.getValue(),e.getMessage()).toJSONString();
        }
    }

}
