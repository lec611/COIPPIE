package edu.seu.dao;

import edu.seu.model.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/30
 */
@Mapper
public interface AnswerDao {
    String TABLE_NAME = "answer";
    String COMMON_INSERT_FIELD = "user,park,year,invest";

    @Insert({"insert into ", TABLE_NAME, "(", COMMON_INSERT_FIELD, ",environment,score_en) values(#{user},#{park},#{year},#{invest},#{environment},#{scoreEn})"})
    Integer insertEnvironment(@Param("user") String user, @Param("park") String park, @Param("year") String year, @Param("invest") String invest, @Param("environment") String environment, @Param("scoreEn") Double scoreEn);

    @Insert({"insert into ", TABLE_NAME, "(", COMMON_INSERT_FIELD, ",process,score_pr) values(#{user},#{park},#{year},#{invest},#{process},#{scorePr})"})
    Integer insertProcess(@Param("user") String user, @Param("park") String park, @Param("year") String year, @Param("invest") String invest, @Param("process") String process, @Param("scorePr") Double scorePr);

    @Insert({"insert into ", TABLE_NAME, "(", COMMON_INSERT_FIELD, ",effect,score_ef) values(#{user},#{park},#{year},#{invest},#{effect},#{scoreEf})"})
    Integer insertEffect(@Param("user") String user, @Param("park") String park, @Param("year") String year, @Param("invest") String invest, @Param("effect") String effect, @Param("scoreEf") Double scoreEf);

    @Insert({"insert into ", TABLE_NAME, "(", COMMON_INSERT_FIELD, ",result,score_re) values(#{user},#{park},#{year},#{invest},#{result},#{scoreRe})"})
    Integer insertResult(@Param("user") String user, @Param("park") String park, @Param("year") String year, @Param("invest") String invest, @Param("result") String result, @Param("scoreRe") Double scoreRe);

    @Update({"update ", TABLE_NAME, " set environment=#{environment},score_en=#{scoreEn} where id=#{id}"})
    Integer updateEnvironment(@Param("id") Integer id, @Param("environment") String environment, @Param("scoreEn") Double scoreEn);

    @Update({"update ", TABLE_NAME, " set process=#{process},score_pr=#{scorePr} where id=#{id}"})
    Integer updateProcess(@Param("id") Integer id, @Param("process") String process, @Param("scorePr") Double scorePr);

    @Update({"update ", TABLE_NAME, " set effect=#{effect},score_ef=#{scoreEf} where id=#{id}"})
    Integer updateEffect(@Param("id") Integer id, @Param("effect") String effect, @Param("scoreEf") Double scoreEf);

    @Update({"update ", TABLE_NAME, " set result=#{result},score_re=#{scoreRe} where id=#{id}"})
    Integer updateResult(@Param("id") Integer id, @Param("result") String result, @Param("scoreRe") Double scoreRe);

    @Update({"update ", TABLE_NAME, " set score_total=#{scoreTotal} where id=#{id}"})
    Integer updateTotal(@Param("id") Integer id, @Param("scoreTotal") Double scoreTotal);

    @Select({"select id from ", TABLE_NAME, "where user=#{user} and park=#{park} and year=#{year} and invest=#{invest}"})
    Integer selectID(@Param("user") String user, @Param("park") String park, @Param("year") String year, @Param("invest") String invest);

    @Select({"select * from ", TABLE_NAME, " where user=#{name} and park=#{park} and year=#{year} and invest=#{invest}"})
    Answer queryAnswer(@Param("name") String name, @Param("park") String park, @Param("year") String year, @Param("invest") String invest);

    //    @Select({"select * from ",TABLE_NAME," where user=#{name} and park=#{park}"})
//    List<Answer> queryByPark(@Param("name")String name,@Param("park")String park);
//
//    @Select({"select * from ",TABLE_NAME," where user=#{name} and year=#{year}"})
//    List<Answer> queryByYear(@Param("name")String name,@Param("year")String year);
//
//    @Select({"select * from ",TABLE_NAME," where user=#{name} and invest=#{invest}"})
//    List<Answer> queryByInvest(@Param("name")String name,@Param("invest")String invest);
    @Select({"select * from ", TABLE_NAME, " where  park=#{park}"})
    List<Answer> queryByPark(@Param("park") String park);

    @Select({"select * from ", TABLE_NAME, " where  year=#{year}"})
    List<Answer> queryByYear(@Param("year") String year);

    @Select({"select * from ", TABLE_NAME, "  invest=#{invest}"})
    List<Answer> queryByInvest(@Param("invest") String invest);

    @Select({"select count(id ) from", TABLE_NAME ,"where user=#{name} and park=#{park} and year=#{year} and invest=#{invest}"})
    int isExist(@Param("name") String name, @Param("park") String park, @Param("year") String year, @Param("invest") String invest);
}
