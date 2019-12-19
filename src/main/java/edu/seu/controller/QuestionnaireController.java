package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.seu.model.Questionnaire;
import edu.seu.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wjx
 * @date 19/10/29
 */
@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @ResponseBody
    @RequestMapping(value = "/questionnaire",produces = "text/html;charset=UTF-8")
    public String questionnaire(HttpServletRequest request){
        String type = request.getParameter("type");
        List<Questionnaire> questionnaireList = questionnaireService.showQuestionnaire(type);

        return JSON.toJSONString(toString(questionnaireList));
    }

    private String toString(List<Questionnaire> list){
        String module,questions,optionType,options,score;

        //将数据库中的option字段xxx,yyy变为"xxx","yyy"
        for(int i=0;i<list.size();i++){
            if(list.get(i).getOptionType() == 0) {
                String temp = list.get(i).getOption();
                String[] str = temp.split(",");
                temp = "";
                for (int j = 0; j < str.length; j++) {
                    temp += "\"" + str[j] + "\"";
                    if (j != str.length - 1) {
                        temp += ",";
                    }
                }
                list.get(i).setOption(temp);
            }
        }

        module = "[\"";
        questions = "[[\"";
        optionType = "[[";
        options = "[[[";
        score = "[[[";
        for(int i=0;i<list.size();i++){
            if( i == 0 || !list.get(i).getModule().equals(list.get(i-1).getModule())) {
                module += list.get(i).getModule();
                if (!list.get(i).getModule().equals(list.get(list.size() - 1).getModule())) {
                    module += "\",\"";
                }
            }
            if( i != 0 && list.get(i).getQuestion().substring(0,1).equals("1")) {
                questions += "\"],[\"";
                optionType += "],[";
                options += "]],[[";
                score += "]],[[";
            }else if(i != 0){
                questions += "\",\"";
                optionType += ",";
                options += "],[";
                score += "],[";
            }
            questions += list.get(i).getQuestion();
            optionType += list.get(i).getOptionType();
            options += list.get(i).getOption();
            score += list.get(i).getScore();
            if(i == list.size() - 1){
                questions += "\"]]";
                optionType += "]]";
                options += "]]]";
                score += "]]]";
            }
        }
        module += "\"]";

        //格式定制返回
        String format = "{\"module\":"+module+",\"questions\":"+questions+",\"optionType\":"+optionType+",\"options\":"+options+",\"score\":"+score+"}";

        System.out.println(format);
        return format;
    }
}

//        前端返回格式示例
//        {
//        "module":["宏观环境评估","中观环境评估"],
//        "questions":[["1.国家外交关系","2.合作国宏观政治形势与社会环境稳定程度"],["1.地方外交关系（园区所在城市与中方开发单位所在城市）"]],
//        "optionType":[[0,0],[0]],
//        "options":[[["全天候战略合作伙伴关系","全面战略协作伙伴关系","全面战略合作伙伴关系","全面战略伙伴关系"],["十分稳定","比较稳定","一般","不稳定"]],[["复合型友好城市关系","单一型友好城市关系","已有民间交流合作","尚无民间交流合作"]]],
//        "score":[[[90,90,90,80],[90,80,60,40]],[[90,80,60,40]]]
//        }
