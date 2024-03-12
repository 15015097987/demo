package com.example.demo.controller;

import com.example.demo.pojo.*;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/article")
public class ArticleController {
     public Article article;

    @Autowired//没有这个就会出现articleService is null
    private ArticleService articleService;


    //文章的添加
    @PostMapping
    public Result add(@RequestBody  @Validated Article article){

        articleService.add(article);
        return Result.success();

    }
    //文章的管理列表
    @GetMapping
    public  Result<PageBean<Article>>list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
       PageBean<Article> pb= articleService.list(pageNum,pageSize,categoryId,state);
       return Result.success(pb);
    }
    //文章删除
    @DeleteMapping
    public Result delete(Integer id){

        articleService.delete(id);

        return Result.success();
    }
    //文章的主页显式
    @GetMapping("/Recommendation")
    public  Result<PageBean<Article>>articleList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer creatorId
    ) {
        PageBean<Article> pb = articleService.articleList(pageNum, pageSize, categoryId, creatorId);
        return Result.success(pb);
    }

    //从前段拿取到文章的id
    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
        article = articleService.detail(id);
        return Result.success(article);

    }
    //返回文章的详细
    @GetMapping("/articleShow")
    public Result<Article> detail() {
        return Result.success(article);

    }

    //评论区
    //添加评论
    @PostMapping("/Comment")
    public Result CMadd(@RequestBody  @Validated Comment comment){
        articleService.CMadd(comment,article.getId());
        return Result.success();
    }

    //展示评论数据
    @GetMapping("/Comment")
    public  Result<PageBean<Comment>>commentList(
            Integer pageNum,
            Integer pageSize
    ) {
        PageBean<Comment> pb = articleService.commentList(pageNum, pageSize, article.getId());
        System.out.println(article.getId());
        return Result.success(pb);
    }

}
