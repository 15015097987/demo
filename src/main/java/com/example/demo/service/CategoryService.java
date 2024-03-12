package com.example.demo.service;

import com.example.demo.pojo.Category;

import java.util.List;

public interface CategoryService {

    //新增文章
    void add(Category category);

    //列表查询
    List<Category> list();

    //根据Id查询分类信息
    Category findById(Integer id);

    //更新文章
    void update(Category category);

    //删除文章
    void delete(Integer id);
}
