package com.jeffrey.controller;

import com.jeffrey.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originfilename = file.getOriginalFilename();
        //利用uuid保证文件名唯一
        String filename = UUID.randomUUID().toString() + originfilename.substring(originfilename.lastIndexOf("."));
        file.transferTo(new File("E:\\big-event\\src\\main\\resources\\files\\"+filename));
        return Result.success("url访问地址");
    }
}
