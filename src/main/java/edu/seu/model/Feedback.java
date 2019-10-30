package edu.seu.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wjx
 * @date 19/10/26
 */
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Feedback {
    //反馈填写用户名
    private String user;

    //是否达到使用需求
    private String rq1;

    //值得优化的地方
    private String rq2;

    //高质量发展建议
    private String rq3;

    public Feedback(){ }

}
