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
public class Document {
    private String park;
    private String year;
    private String invest;
    private String nation;
    private String industry;
    private String schedule;
    private String assess;
    private String build;
    private String construct;
    private String operate;
    private String image;
    private String user;
    private String career;
    private String company;
    private String phone;
    private String email;
    private String address;

    public Document(){ }
}
