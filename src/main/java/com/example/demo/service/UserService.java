package com.example.demo.service;


import com.example.demo.pojo.Category;
import com.example.demo.pojo.User;

import java.util.List;

public interface UserService {
    static void delete(Integer id) {
    }

    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username ,String password);

    //更新
    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    //更新密码
    void updatePwd(String newPwd);

    //用户列表查询
    List<User> list();
}
