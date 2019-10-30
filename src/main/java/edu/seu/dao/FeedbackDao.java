package edu.seu.dao;

import edu.seu.model.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/27
 */
@Mapper
public interface FeedbackDao {
    String TABLE_NAME = "feedback";
    String FIELD = "user,rq1,rq2,rq3";

    Integer persist(Feedback feedback);

    @Select({"select",FIELD,"from",TABLE_NAME})
    List<Feedback> selectAll();
}
