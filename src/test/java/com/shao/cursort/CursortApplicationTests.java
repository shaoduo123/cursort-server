package com.shao.cursort;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.ObjectListing;
import com.shao.cursort.utils.OSSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CursortApplicationTests {

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
        listFile("root/1/files");
    }

    /**
     * 列举文件下所有的文件url信息
     */
    public  List<String> listFile(String fileHost) {
        // 创建OSSClient实例。
        OSSUtil ossClient = new OSSUtil(endpoint, accessKeyId, accessKeySecret, bucketName, uploadPath);
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(fileHost );
        // 列出文件。
        ObjectListing listing = ossClient.getOSSClient().listObjects(listObjectsRequest);
        // 遍历所有文件。
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listing.getObjectSummaries().size(); i++) {
            if (i == 0) {
                continue;
            }
            //  FILE_URL = "https://" + bucketName + "." + endpoint + "/" + listing.getObjectSummaries().get(i).getKey();
            String FILE_URL = listing.getObjectSummaries().get(i).getKey();
            System.out.println(FILE_URL);
            list.add(FILE_URL);
        }
        // 关闭OSSClient。
        System.out.println(list.size());
        ossClient.getOSSClient().shutdown();
        return list;

    }
}
