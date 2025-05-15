package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     * 本地磁盘上传文件
     * @param name
     * @param age
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/local")
    public Result uploadOnLocalDisk(String name, Integer age, MultipartFile file) throws IOException {
        log.info("name:{}, age:{}, file:{}", name, age, file.getOriginalFilename());
        // 保存文件
        String originalFilename = file.getOriginalFilename();
        // 新的文件名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;
        // 本地磁盘保存文件到指定目录
        file.transferTo(new File("D:/Graduate/JavaPro/Web-Ai/tlias-web-management/Test_images" + newFileName));
        return Result.success();
    }
    /**
     * 阿里云上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info(" file:{}",file.getOriginalFilename());
        // 上传文件到阿里云OSS
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件生产OSS url:{}",url);
        return Result.success(url);
    }
}
