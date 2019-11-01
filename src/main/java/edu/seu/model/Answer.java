package edu.seu.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wjx
 * @date 19/10/30
 */
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Answer {
    private String user;
    private String park;
    private String year;
    private String invest;
    private String environment;
    private String process;
    private String effect;
    private String result;
    private Double scoreEn;
    private Double scorePr;
    private Double scoreEf;
    private Double scoreRe;
    private Double scoreTotal;

    public Answer() { }

}
