package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        //把文件的内容储存到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证当前的文件名字是为一的，从而防止文件覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("F:\\java学习\\编程技术\\Java\\代码\\demo\\file\\" +filename));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}
