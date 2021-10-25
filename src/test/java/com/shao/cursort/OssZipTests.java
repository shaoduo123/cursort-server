package com.shao.cursort;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import com.shao.cursort.utils.OSSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SpringBootTest
class OssZipTests {

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
    void contextLoads() throws IOException {
        String folderPath = "root/1/files/";
        String zipName  ="testZip" ;
        OSSUtil ossUtil = new OSSUtil(endpoint, accessKeyId, accessKeySecret, bucketName, uploadPath);
        //  ossUtil.deleteFile(bucketName,"root/1/files/two/");
        OSS oss = ossUtil.getOSSClient();
        //  CopyObjectResult result = oss.copyObject(bucketName, "root/1/files/one/", bucketName, "root/1/files/four/");
        File zipFile = File.createTempFile("abc", ".zip");
        FileOutputStream f = new FileOutputStream(zipFile);
        /**
         * 作用是为任何OutputStream产生校验和
         * 第一个参数是制定产生校验和的输出流，第二个参数是指定Checksum的类型 （Adler32（较快）和CRC32两种）
         */
        CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
        // 用于将数据压缩成Zip文件格式
        ZipOutputStream zos = new ZipOutputStream(csum);

        //listFile("root/1/files");

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setPrefix(folderPath);  //指定下一级文件
        listObjectsRequest.setMarker(""); //获取下一页的起始点，它的下一项
        listObjectsRequest.setMaxKeys(1000);//设置分页的页容量
//            listObjectsRequest.setDelimiter("/");//跳出递归循环，只去指定目录下的文件。使用它时 Prefix文件路径要以“/”结尾
        ObjectListing objectListing = oss.listObjects(listObjectsRequest);
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (OSSObjectSummary ossfile : objectSummaries) {

            // 获取Object，返回结果为OSSObject对象
            OSSObject object = oss.getObject(bucketName, ossfile.getKey());
            // 读去Object内容  返回
            InputStream inputStream = object.getObjectContent();
            // 对于每一个要被存放到压缩包的文件，都必须调用ZipOutputStream对象的putNextEntry()方法，确保压缩包里面文件不同名
            String replace = ossfile.getKey().replace(folderPath, "");
            //split[split.length]
            zos.putNextEntry(new ZipEntry(replace));
            int bytesRead = 0;
            // 向压缩文件中输出数据
            while ((bytesRead = inputStream.read()) != -1) {
                zos.write(bytesRead);
            }
            inputStream.close();
            // 当前文件写完，定位为写入下一条项目
            zos.closeEntry();
        }
        zos.close();
        FileInputStream fis = new FileInputStream(zipFile);
        //重新上传到OSS里
        oss.putObject(bucketName, "root/1/zip/" + zipName + ".zip", fis);
        oss.shutdown();
        // 关闭流
        fis.close();
        oss.shutdown();
        // 删除临时文件
        zipFile.delete();
    }
}
