package com.example.demo.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    @NotNull
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore//让springmvc把当前对象转成jso字符串的时候,或略password,最终的json字符串中就没有password这个属性了
    private String password;//密码
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称
    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    //数据库的命名是_下划线命名,但是实体类是驼峰命名
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private Integer manage;//判断管理员
    private Integer permissions;//权限
}
