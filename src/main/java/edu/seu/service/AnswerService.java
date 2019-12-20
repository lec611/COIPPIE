package edu.seu.service;

import edu.seu.base.CodeEnum;
import edu.seu.dao.AnswerDao;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.Answer;
import edu.seu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/31
 */
@Service
public class AnswerService {
    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    private String currentUser;
    private String currentPark;
    private String currentYear;
    private String currentInvest;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentPark() {
        return currentPark;
    }

    public void setCurrentPark(String currentPark) {
        this.currentPark = currentPark;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getCurrentInvest() {
        return currentInvest;
    }

    public void setCurrentInvest(String currentInvest) {
        this.currentInvest = currentInvest;
    }

    /**
     * 上载用户评估结果push
     */
    public void uploadAnswer(String type,String data,double score){
        String name = userService.getCurrentUser().getName();
        String park = documentService.getCurrentPark();
        String year = documentService.getCurrentYear();
        String invest = documentService.getCurrentInvest();


        //若仍是该用户针对方才所填档案的评估回答，则执行更新操作
        if(name.equals(getCurrentUser()) && park.equals(getCurrentPark()) && year.equals(getCurrentYear()) && invest.equals(getCurrentInvest())) {
            int id = answerDao.selectID(name,park,year,invest);
            if (type.equals("environment")) {
                answerDao.updateEnvironment(id,data,score);
            }else if (type.equals("process")){
                answerDao.updateProcess(id,data,score);
            }else if (type.equals("effect")){
                answerDao.updateEffect(id,data,score);
            }else if (type.equals("result")){
                answerDao.updateResult(id,data,score);
            }
        }
        //若不是上次记录用户针对上次记录档案的评估回答，则执行插入操作
        else{
            setCurrentUser(name);
            setCurrentPark(park);
            setCurrentYear(year);
            setCurrentInvest(invest);
            if (type.equals("environment")) {
                answerDao.insertEnvironment(name, park, year, invest, data, score);
            }else if (type.equals("process")){
                answerDao.insertProcess(name,park,year,invest,data,score);
            }else if (type.equals("effect")){
                answerDao.insertEffect(name,park,year,invest,data,score);
            }else if (type.equals("result")){
                answerDao.insertResult(name,park,year,invest,data,score);
            }
        }
    }

    /**
     * 生成评估报告时判断相应模块是否已填写并提交
     */
    public boolean isSubmitted(String type){
        String name = userService.getCurrentUser().getName();
        String park = documentService.getCurrentPark();
        String year = documentService.getCurrentYear();
        String invest = documentService.getCurrentInvest();

        if(name.equals(getCurrentUser()) && park.equals(getCurrentPark()) && year.equals(getCurrentYear()) && invest.equals(getCurrentInvest())) {
            Answer answer = answerDao.queryAnswer(name,park,year,invest);
            if (type.equals("environment")) {
                return answer.getEnvironment() != null;
            }else if (type.equals("process")){
                return answer.getProcess() != null;
            }else if (type.equals("effect")){
                return answer.getEffect() != null;
            }else if (type.equals("result")){
                return answer.getResult() != null;
            }else if (type.equals("total")){
                if(answer.getEnvironment() != null&&answer.getProcess() != null&&answer.getEffect() != null&&answer.getResult() != null){
                    double scoreTotal = 0.0;
                    String[] strEn = answer.getEnvironment().split("[,\\;]");
                    String[] strPr = answer.getProcess().split("[,\\;]");
                    String[] strEf = answer.getEffect().split(",|\\;");
                    String[] strRe = answer.getResult().split(",|\\;");
                    for (String s : strEn) {
                        scoreTotal += Double.parseDouble(s);
                    }
                    for(String s : strPr){
                        scoreTotal += Double.parseDouble(s);
                    }
                    for (String s : strEf) {
                        scoreTotal += Double.parseDouble(s);
                    }
                    for (String s : strRe) {
                        scoreTotal += Double.parseDouble(s);
                    }
                    scoreTotal /= strEn.length+strPr.length+strEf.length+strRe.length;
                    int id = answerDao.selectID(name,park,year,invest);
                    answerDao.updateTotal(id,scoreTotal);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *根据关键字匹配评估条目
     */
    public List<Answer> queryByCondition(String condition,String key) throws OICPMPIEExceptions {
        String name = userService.getCurrentUser().getName();
        if(name == null){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR,"用户未登录");
        }

        List<Answer> answerList = null;
        if(condition.equals("park")){
            answerList = answerDao.queryByPark(name,key);
        }else if (condition.equals("year")){
            answerList = answerDao.queryByYear(name,key.substring(0,4));
        }else if (condition.equals("invest")){
            answerList = answerDao.queryByInvest(name,key);
        }
        return answerList;
    }

}
