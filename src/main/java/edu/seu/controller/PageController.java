package edu.seu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lec
 * @Date: 2019/10/14
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
