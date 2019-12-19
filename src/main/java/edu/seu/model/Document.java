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
    private String park;   //园区名称
    private String year;    //建区年份
    private String invest;  // 投资单位
    private String nation;  // 所在国家
    private String industry; //规划产业
    private String schedule;  //规划规模
    private String assess;  //评估规模
    private String build;   //实建规模
    private String construct;   //建设单位
    private String operate; //运营单位
    private String image;   //规划平面图
    private String user;
    private String career;
    private String company;
    private String phone;
    private String email;
    private String address;

    public Document(){ }
}
