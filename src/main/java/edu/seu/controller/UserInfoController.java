package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.seu.model.User;
import edu.seu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wjx
 * @date 2019/10/10
 */
@RequestMapping("/userInfo")
@Controller
public class UserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/initShow")
    public String initShow(){
        User currentUser = userService.getCurrentUser();

        User user = userService.getUser(currentUser.getName());
        JSONObject object = new JSONObject();
        object.put("name",user.getName());
        object.put("password",user.getPassword());
        object.put("email",user.getEmail());
        object.put("phoneNum",user.getPhoneNum());
        object.put("sex",user.getSex());
        object.put("company",user.getCompany());
        object.put("address",user.getAddress());
        object.put("domain",user.getDomain());
        return JSON.toJSONString(object.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/updateInfo")
    public String updateInfo(HttpServletRequest request){
        User user = userService.getCurrentUser();
        String name = user.getName();

        String phoneNum = request.getParameter("phoneNum");
        String sex = request.getParameter("sex");
        String company = request.getParameter("company");
        String address = request.getParameter("address");
        String domain = request.getParameter("domain");

        userService.updateUserInfo(name,phoneNum,sex,company,address,domain);

        user = userService.getCurrentUser();
        System.out.println(user);

        return JSON.toJSONString("success");
    }

}