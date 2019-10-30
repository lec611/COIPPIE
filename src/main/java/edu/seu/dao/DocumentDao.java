package edu.seu.dao;

import edu.seu.model.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/27
 */
@Mapper
public interface DocumentDao {
    String TABLE_NAME = "document";
    String FIELD = "park,year,invest,nation,industry,schedule,assess,build,construct,operate,image,user,career,company,phone,email,address";

    Integer persist(Document document);
    List<Document> showByYear(@Param("user") String user, @Param("year")String year);

    @Select({"select ",FIELD," from ",TABLE_NAME," where user=#{user} and park=#{park}"})
    List<Document> showByPark(@Param("user") String user, @Param("park")String park);

    @Select({"select ",FIELD," from ",TABLE_NAME," where user=#{user} and invest=#{invest}"})
    List<Document> showByInvest(@Param("user") String user, @Param("invest")String invest);

}
