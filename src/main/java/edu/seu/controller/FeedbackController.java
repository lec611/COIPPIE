package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.seu.base.CodeEnum;
import edu.seu.base.CommonResponse;
import edu.seu.exceptions.COIPPIEExceptions;
import edu.seu.model.Feedback;
import edu.seu.model.User;
import edu.seu.service.FeedbackService;
import edu.seu.service.UserService;
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
 * @date 19/10/26
 */

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    public static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    @ResponseBody
    @RequestMapping("/insert")
    public String insert(HttpServletRequest request){
        User user = userService.getCurrentUser();
        Feedback feedback = new Feedback();
        feedback.setUser(user.getName());

        feedback.setRq1(request.getParameter("rq1"));
        feedback.setRq2(request.getParameter("rq2"));
        feedback.setRq3(request.getParameter("rq3"));

        feedbackService.insertFeedback(feedback);

        return JSON.toJSONString("success");
    }

    @ResponseBody
    @RequestMapping("/showAll")
    public String showAll(){
        try {
            List<Feedback> feedbackList = feedbackService.showAll();
            JSONArray array = new JSONArray();
            for (int i = 0; i < feedbackList.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("user", feedbackList.get(i).getUser());
                object.put("rq1", feedbackList.get(i).getRq1());
                object.put("rq2", feedbackList.get(i).getRq2());
                object.put("rq3", feedbackList.get(i).getRq3());
                array.add(object);
            }
            return JSON.toJSONString(array.toString());
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new CommonResponse(CodeEnum.USER_ERROR.getValue(),e.getMessage()).toJSONString();
        }
    }

}
