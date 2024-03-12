package com.example.demo.service.impl;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.pojo.Article;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.PageBean;
import com.example.demo.service.ArticleService;
import com.example.demo.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceimpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);

    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1.创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询pageMelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper

            Map<String, Object> map = ThreadLocalUtil.get();
            if((Integer) map.get("id") == 1){
                List<Article> as = articleMapper.Mlist(categoryId, state);
                //Page中提供了方法，可以提供PageHelper分页查询后，得到的总的记录条数和当前页的数据
                Page<Article> p = (Page<Article>) as;
                //把数据填充到PageBean对象中
                pb.setTotal(p.getTotal());
                pb.setItems(p.getResult());

            }else{
                Integer userId = (Integer) map.get("id");
                List<Article> as = articleMapper.list(userId, categoryId, state);
                //Page中提供了方法，可以提供PageHelper分页查询后，得到的总的记录条数和当前页的数据
                Page<Article> p = (Page<Article>) as;
                //把数据填充到PageBean对象中
                pb.setTotal(p.getTotal());
                pb.setItems(p.getResult());
            }

        return pb;
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

    @Override
    public PageBean<Article> articleList(Integer pageNum, Integer pageSize, Integer categoryId, Integer creatorId) {
        //1.创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询pageMelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper
        List<Article> as = articleMapper.articleList(categoryId, creatorId, "已发布");
        //Page中提供了方法，可以提供PageHelper分页查询后，得到的总的记录条数和当前页的数据
        Page<Article> p = (Page<Article>) as;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public Article detail(Integer articleId) {
        //用文章的id去查询到文章的数据
        Article article = articleMapper.detail(articleId, "已发布");
        articleMapper.addviews(articleId);
        return article;
    }
    //添加评论
    @Override
    public void CMadd(Comment comment, Integer id) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        comment.setCreateUser(userId);
        comment.setArticleId(id);
        articleMapper.CMadd(comment);

    }

    @Override
    public PageBean<Comment> commentList(Integer pageNum, Integer pageSize, Integer articleId) {
        //1.创建PageBean对象
        PageBean<Comment> pb = new PageBean<>();

        //2.开启分页查询pageMelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper
        List<Comment> as = articleMapper.commentList(articleId,"已发布");
        //Page中提供了方法，可以提供PageHelper分页查询后，得到的总的记录条数和当前页的数据
        Page<Comment> p = (Page<Comment>) as;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

}
