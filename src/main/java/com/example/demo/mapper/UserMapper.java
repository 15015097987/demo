package com.example.demo.mapper;


import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    @Insert("insert into user(username,password,create_time,update_time)" + "values(#{username},#{password},now(),now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id = #{id}")
    void updata(User user);

    @Update("update user set user_pic=#{avatarUrl}, update_time =now() where id = #{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update  user set password=#{md5String} ,update_time=now() where id =#{id}")
    void updataPwd(String md5String, Integer id);

    @Select("select * from user ")
    List<User> list();
}
