package com.demo.minio.controller;

import com.demo.minio.service.MyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tianwenyuan
 * Date: 2024/1/5
 * Time: 16:25
 */
@RestController
@RequestMapping(value = "/file", method = RequestMethod.POST)
public class FileUploadController {

    @Autowired
    private MyFileService myFileService;

    @RequestMapping("/upload")
    public void upload() {
        String filename = "/Users/user/Demo/spring-boot-minio-demo/HELP.md";
        myFileService.upload(filename);
    }
}
