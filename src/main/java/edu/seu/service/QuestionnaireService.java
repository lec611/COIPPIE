package edu.seu.service;

import edu.seu.dao.QuestionnaireDao;
import edu.seu.model.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/29
 */
@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireDao questionnaireDao;

    public List<Questionnaire> showQuestionnaire(String type){
        return questionnaireDao.showQuestionnaire(type);
    }

    public List<String> showQuestion(String type){
        return questionnaireDao.showQuestion(type);
    }
}
