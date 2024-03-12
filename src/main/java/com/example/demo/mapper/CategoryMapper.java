package com.example.demo.mapper;

import com.example.demo.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    //查询分类
    @Select("select * from category ")
    List<Category> list();

    //根据Id查询
    @Select("select * from category where id =#{id}")
    Category findById(Integer id);

    //更新文章类别查询
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id =#{id}")
    void update(Category category);

    //删除文章类别
    @Delete("delete  from category where id =#{id}")
    void delete(Integer id);
}
