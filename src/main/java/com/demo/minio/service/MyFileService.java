package com.demo.minio.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tianwenyuan
 * Date: 2024/1/5
 * Time: 15:10
 */
public interface MyFileService {

    /**
     * 上传
     *
     * @param fileName 文件名
     */
    void upload(String fileName);

    void upload(String fileName, String object);

    void upload(String fileName, String object, String bucket);
}
