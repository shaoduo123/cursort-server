package com.shao.cursort;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.ObjectListing;
import com.shao.cursort.utils.OSSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OssCopyTests {

    @Value("${oss.endpoint}")
    private String endpoint ;

    @Value("${oss.access-key-id}")
    private String accessKeyId ;

    @Value("${oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${oss.bucket-name}")
    private String bucketName;

    @Value("${oss.upload-path}")
    private String uploadPath ;

    @Test
    void contextLoads() {
        OSSUtil ossUtil = new OSSUtil(endpoint,accessKeyId,accessKeySecret,bucketName,uploadPath) ;
      //  ossUtil.deleteFile(bucketName,"root/1/files/two/");
        OSS oss =ossUtil.getOSSClient() ;
        CopyObjectResult result = oss.copyObject(bucketName, "root/1/files/one/", bucketName, "root/1/files/four/");
        //listFile("root/1/files");
    }

}
