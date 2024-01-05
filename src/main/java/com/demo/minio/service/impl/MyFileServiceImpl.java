package com.demo.minio.service.impl;

import com.demo.minio.service.MyFileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tianwenyuan
 * Date: 2024/1/5
 * Time: 15:13
 */
@Service
public class MyFileServiceImpl implements MyFileService {

    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(MyFileServiceImpl.class);

    /**
     * minio 客户端
     */
    @Autowired
    private MinioClient minioClient;

    /**
     * 默认存储桶名称
     */
    @Value("${minio.bucket-name}")
    private String defaultBucketName;

    @Override
    public void upload(String fileName) {
        uploadObject(fileName, null, defaultBucketName);
    }

    @Override
    public void upload(String fileName, String object) {
        uploadObject(fileName, object, defaultBucketName);
    }

    @Override
    public void upload(String fileName, String object, String bucket) {
        uploadObject(fileName, object, bucket);
    }

    /**
     * 上传
     *
     * @param fileName 文件名
     * @param object
     * @param bucket
     */
    public void uploadObject(String fileName, String object, String bucket) {
        if (!StringUtils.hasLength(fileName) || !StringUtils.hasLength(bucket)) {
            return;
        }
        try {
            // 存储桶构建
            bucketBuild(bucket);
            // 保存的文件名称
            object = !StringUtils.hasLength(object) ? fileName.substring(fileName.lastIndexOf("/") > 0 ? fileName.lastIndexOf("/") : fileName.lastIndexOf("\\")) : object;

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .filename(fileName)
                            .build());
            LOG.info(fileName + " is successfully uploaded as object" + object + " to bucket " + bucket + ".");
        } catch (Exception exception) {
            LOG.error("upload error", exception);
        }
    }

    /**
     * 存储桶构建
     *
     * @param bucketName
     */
    private void bucketBuild(String bucketName) {
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                LOG.info("Bucket " + bucketName + " make success.");
            } else {
                LOG.info("Bucket " + bucketName + " already exists.");
            }
        } catch (Exception exception) {
            LOG.error("bucketBuild error", exception);
        }
    }

}
