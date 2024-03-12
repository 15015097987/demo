package com.example.demo.mapper;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.Insert;

public interface CommentMapper {
    //新增
    @Insert("insert into comment(content,state,create_user,create_time,update_time,likes)"+
            "values (#{content},#{state},#{createUser},#{createTime},#{updateTime},#{likes})")
    void add(Comment comment);
}
