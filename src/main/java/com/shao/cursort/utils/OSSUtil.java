package com.shao.cursort.utils;

/**
 * 阿里云OSS工具类
 */

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;

public class OSSUtil {

    /**
     *
     * @Title: getOSSClient
     * @Description: 获取oss客户端
     * @return OSSClient oss客户端
     * @throws
     */
    private String endpoint ;
    private String accessKeyId ;
    private String accessKeySecret ;
    private String bucketName ;
    private String basePath ;

    public OSSUtil(String endpoint,String accessKeyId,String accessKeySecret,String bucketName, String basePath){
        this.endpoint = endpoint ;
        this.accessKeySecret = accessKeySecret ;
        this.accessKeyId = accessKeyId ;
        this.bucketName = bucketName ;
        this.basePath = basePath ;
    }
    public  OSS getOSSClient() {
        //使用你的对应的endpoint地址
//        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
//        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "你的accessKeyId";
//        String accessKeySecret = "你的accessKeySecret";
       // OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret) ;
        return ossClient;
    }

    /**
     *
     * @Title: uploadByNetworkStream
     * @Description: 通过网络流上传文件
     * @param url 			URL
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名）例如“test/index.html”
     * @return void 		返回类型
     * @throws
     */
    public  void uploadByNetworkStream( URL url, String bucketName, String objectName) {
        OSS ossClient = null;
        try {
            ossClient = getOSSClient() ;
            InputStream inputStream = url.openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     *
     * @Title: uploadByInputStream
     * @Description: 通过输入流上传文件
     * @param inputStream 	输入流
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void 		返回类型
     * @throws
     */
    public  void uploadByInputStream(InputStream inputStream, String bucketName,
                                           String objectName) {
        OSS ossClient = null;
        try {
            ossClient = getOSSClient() ;
            PutObjectResult result =  ossClient.putObject(bucketName, objectName, inputStream);
          if(result!=null){
              System.out.println("上传成功");

          }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public  String getUrl(String objectName,String type,int width,int height){
        // 设置图片处理样式。
       // String style = "image/resize,m_fixed,w_100,h_100/rotate,90";
        //String imgStyle = "image/resize,m_fixed,w_"+width+",h_"+height+"/rotate,90";
        //https://help.aliyun.com/document_detail/44688.html?spm=5176.10695662.1996646101.searchclickresult.3b7d7392ux4GaH#title-y1e-xd2-5oo
        String imgStyle = "image/resize" ;
        if(!type.equals("")){
            imgStyle +=",m_"+type ;
        }else{
            imgStyle="";
        }
        if(width!=0){
            imgStyle +=",w_"+width ;
        }
        if(height!=0){
            imgStyle +=",h_"+height ;
        }

        OSS ossClient = null;
        try {
            ossClient = getOSSClient();
            Date expiration = new Date(new Date().getTime() + 200 * 1000); //文件在200s后过期
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(this.bucketName, objectName, HttpMethod.GET);
            req.setExpiration(expiration);
            req.setProcess(imgStyle);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            // 关闭OSSClient。
//        logger.info("------OSS文件文件信息--------" + signedUrl.toString());
            ossClient.shutdown();
            if (signedUrl != null) {
                return signedUrl.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }



    /**
     *
     * @Title: uploadByFile
     * @Description: 通过file上传文件
     * @param file 			上传的文件
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void 		返回类型
     * @throws
     */
    public  void uploadByFile( File file, String bucketName, String objectName) {
        OSS ossClient = null;
        try {
            ossClient = getOSSClient();
            ossClient.putObject(bucketName, objectName, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }


    /**
     * 文件复制的特点： 1.复制文件夹的话不能复制里的东西，
     * 2.具体文件只能复制到具体文件 xx/aa.png -> zz/bb.png ， 又比如 文件夹a —>文件夹b
     * 3. 复制具体文件能创建父文件夹
     * @param sourceFile
     * @param toFile
     */
    public void copy(String sourceFile,String toFile){
        OSS ossClient = null;
        try {
            ossClient = getOSSClient();
            ossClient.copyObject(bucketName,sourceFile,bucketName,toFile) ;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ossClient!=null)
                ossClient.shutdown();
        }
    }


    /**
     *
     * @Title: deleteFile
     * @Description: 根据key删除oss服务器上的文件
     * @param bucketName		bucket名称
     * @param objectName    		文件路径/名称，例如“test/a.txt”
     * @return void    		返回类型
     * @throws
     */
    public  void deleteFile(String bucketName, String objectName) {
        OSS ossClient = null;
        try {
            ossClient = getOSSClient();
            ossClient.deleteObject(bucketName, objectName);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ossClient!=null)
              ossClient.shutdown();
        }
    }

    /**
     *
     * @Title: getInputStreamByOSS
     * @Description:根据key获取服务器上的文件的输入流
     * @param bucketName 	bucket名称
     * @param key 			文件路径和名称
     * @return InputStream 	文件输入流
     * @throws
     */
    public  InputStream getInputStreamByOSS(String bucketName, String key) {
        InputStream content = null;
        OSS ossClient = null;
        try {
            ossClient = getOSSClient();
            OSSObject ossObj = ossClient.getObject(bucketName, key);
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(ossClient!=null)
                ossClient.shutdown();
        }
        return content;
    }

    /**
     *
     * @Title: queryAllObject
     * @Description: 查询某个bucket里面的所有文件
     * @param bucketName		bucket名称
     * @return List<String>  文件路径和大小集合
     * @throws
     */
    public  List<String> queryAllObject( String bucketName) {
        List<String> results = new ArrayList<String>();
        OSS ossClient = null;
        try {
            ossClient = getOSSClient();
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                results.add(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return results;
    }


    /**
     * 创建文件夹
     *
     * @param folder
     * @return
     */
    public  String createFolder(String folder) {
        // 创建OSSClient实例。
        OSS ossClient = getOSSClient();
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            //logger.info("创建文件夹成功");
            System.out.println("创建文件夹成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            ossClient.shutdown();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    public void zip(String zipName, String toZipPath, List<String> file)  {
        try {

        OSS oss = getOSSClient();
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
        listObjectsRequest.setPrefix(toZipPath);  //指定下一级文件
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
            String replace = ossfile.getKey().replace(toZipPath, "");
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
        }catch (IOException e)  {
            e.printStackTrace();
        }
    }
}