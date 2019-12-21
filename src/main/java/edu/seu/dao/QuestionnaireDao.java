package edu.seu.dao;

import edu.seu.model.Questionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/29
 */
@Mapper
public interface QuestionnaireDao {
    String TABLE_NAME = "questionnaire";
    String FIELD = "type,module,question,optionType,`option`,score";

    @Select({"select ",FIELD," from ",TABLE_NAME," where type=#{type}"})
    List<Questionnaire> showQuestionnaire(String type);

    @Select({"select question from ",TABLE_NAME," where type=#{type}"})
    List<String> showQuestion(String type);

}
