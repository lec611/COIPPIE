package edu.seu.dao;

import edu.seu.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: wjx
 * @Date: 2019/10/24
 */

@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String SELECT_FIELDS = "id,name,password,email,phoneNum,sex,company,address,domain,isAdmin";

    Integer persist(User user);
    Integer update(User user);
    Integer updateInfo(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
    User selectById(Integer id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where email=#{email}"})
    User selectByEmail(String email);

    @Update({"update ", TABLE_NAME, " set password=#{newPassword} where email=#{email}"})
    Integer updatePassword(@Param("email") String email, @Param("newPassword") String newPassword);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " limit #{begin}, #{end}"})
    List<User> selectAll(@Param("begin")Integer begin, @Param("end") Integer end);

    @Select({"select count(id) from ", TABLE_NAME})
    Integer countAllUser();

}
