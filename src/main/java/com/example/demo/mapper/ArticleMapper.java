package com.example.demo.mapper;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
@MapperScan("")
@Mapper
public interface ArticleMapper {
    //新增
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time,likes,views)"+
    "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime},#{likes},#{views})")
    void add(Article article);

    //文章列表管理
    List<Article> list(Integer userId, Integer categoryId, String state);

    //管理员的文章管理
    List<Article> Mlist(Integer categoryId, String state);

    //删除
    @Delete("delete from article where id =#{id}")
    void delete(Integer id);

    //文章列表展示
    List<Article> articleList(Integer categoryId, Integer creatorId ,String state);

    @Select("select * from article where id =#{articleId} and state =#{state}")
    //文章详细查询
    Article detail(Integer articleId, String state);
    @Update("update article set views = views + 1 where id= #{articleId}")
    void addviews(Integer articleId);

    //添加评论数据
    @Insert("insert into comment(content,state,article_id,create_user,create_time,update_time,likes)"+
            "values (#{content},#{state},#{articleId},#{createUser},#{createTime},#{updateTime},#{likes})")
    void CMadd(Comment comment);

    //展示评论数据
    @Select("select * from comment where article_id =#{articleId} and state =#{state}")
    List<Comment> commentList(Integer articleId,String state);



}
