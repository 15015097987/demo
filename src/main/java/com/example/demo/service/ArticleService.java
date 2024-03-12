package com.example.demo.service;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.PageBean;

public interface ArticleService {
    //新增文章
    void add(Article article);

    //文章条件分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //文章删除
    void delete(Integer id);

    //文章的首页推荐
    PageBean<Article> articleList(Integer pageNum, Integer pageSize, Integer categoryId, Integer creatorId);

    //（文章的详细查询）和（添加浏览量）
    Article detail(Integer articleId);

    //添加评论数据
    void CMadd(Comment comment,Integer id);

    //获取评论数据
    PageBean<Comment> commentList(Integer pageNum, Integer pageSize, Integer articleId);


}
