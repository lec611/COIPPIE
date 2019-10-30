package edu.seu.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wjx
 * @date 19/10/27
 */
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Questionnaire {
    private String type;
    private String module;
    private String question;
    private Integer optionType;
    private String option;
    private String score;

    public Questionnaire(){ }

}
