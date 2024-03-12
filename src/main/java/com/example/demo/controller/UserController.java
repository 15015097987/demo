package com.example.demo.controller;


import com.example.demo.pojo.Category;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,12}$") String username, @Pattern(regexp = "^\\S{5,12}$")String password){
        //注册条件

            //查询用户
            User u= userService.findByUserName(username);
            if(u == null){
                //用户名没被占用,注册用户
                userService.register(username,password);
                return Result.success();

            }else {
                //被占用
                return Result.error("用户名被占用了");

            }


    }

    @PostMapping("/login")
   public Result<String> login(@Pattern(regexp = "^\\S{5,12}$") String username, @Pattern(regexp = "^\\S{5,12}$")String password){
        //根据用户名查询
        User loginUser = userService.findByUserName(username);
        //判断该用户是否存在
        if(loginUser ==null){
            return Result.error("用户名或密码错误");
        }else{
            //判断密码是否正确 loginUser对象中password是加密的
            if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
                //登录成功
                Map<String ,Object>claims =new HashMap<>();
                claims.put("id",loginUser.getId());
                claims.put("username",loginUser.getUsername());
                String token = JwtUtil.genToken(claims);
                //把token存储到redis中
                ValueOperations<String, String> oprations = stringRedisTemplate.opsForValue();
                oprations.set(token,token,12, TimeUnit.HOURS);
                return Result.success(token);
            }
        }
        return  Result.error("用户名或密码错误");
    }
    @GetMapping("/userInfo")
    public  Result<User> userinfo(@RequestHeader(name = "Authorization")String token){
        //根据用户名查询用户
       /* Map<String, Object> map = JwtUtil.parseToken(token);

        String username = (String) map.get("username");*/

        Map<String,Object> map = ThreadLocalUtil.get();

        String username = (String) map.get("username");

        User user = userService.findByUserName(username);

        return Result.success(user);
    }@PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);

        return Result.success();

    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){

        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String>params,@RequestHeader("Authorization")String token){
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");


        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少校验参数");
        }

        //检查老密码是否正确
        //调用service取出密码进行比对
        Map<String,Object> map= ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }
        //校验新密码的是否正确
        if (!newPwd.equals(rePwd)){
            return Result.error("两次填写的新密码不一样");
        }
        //2.调用service更新密码
        userService.updatePwd(newPwd);
        //删除对应的redis中的token
        ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
        operation.getOperations().delete(token);

        return Result.success();
    }
    //用户删除
    @DeleteMapping
    public Result delete(Integer id){

        UserService.delete(id);

        return Result.success();
    }
    @GetMapping
    public Result<List<User>>list(){
        List<User> cs = userService.list();

        return Result.success(cs);
    }
}
