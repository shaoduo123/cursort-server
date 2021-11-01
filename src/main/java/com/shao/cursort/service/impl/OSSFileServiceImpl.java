package com.shao.cursort.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shao.cursort.mapper.FileMapper;
import com.shao.cursort.pojo.File;
import com.shao.cursort.result.FileType;
import com.shao.cursort.result.Result;
import com.shao.cursort.service.FileService;
import com.shao.cursort.utils.Constants;
import com.shao.cursort.utils.OSSUtil;
import com.shao.cursort.utils.SizeUtil;
import com.shao.cursort.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.shao.cursort.result.ResultStatus.*;

@Service
public class OSSFileServiceImpl implements FileService {

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

    @Autowired
    private FileMapper fileMapper ;


    /**
     * 根据路径找到id
     * @param path
     * @return
     */
    @Override
    public String getIdByPath(String path) {
//        Example pathExample = new Example(File.class) ;
//        Example.Criteria  pathCriteria = pathExample.createCriteria() ;
//        pathCriteria.andEqualTo("path");
//        fileMapper.selectOneByExample() ;
        return null;
    }

    @Override
    public Result upload(long userId, MultipartFile file, File floder) {
        try {
            if( file ==null || file.isEmpty())
                return new Result(FAILED_FILE_UPLOAD_EMPTY);

            InputStream is = file.getInputStream() ;
            String fileName = file.getOriginalFilename() ;
            String ossPath = floder.getPath()+fileName ;
            OSSUtil ossUtil = new OSSUtil(endpoint,accessKeyId,accessKeySecret,bucketName,uploadPath) ;
            ossUtil.uploadByInputStream(
                        is,
                        bucketName,
                        ossPath
                        );
            String id = UUIDUtil.UUID() ;
            File f = new File();
            f.setId(id);
            f.setUserId(userId);
            f.setName(fileName);
            f.setPath(ossPath);
            f.setSize(SizeUtil.reSize(file.getSize()));
            //f.setType(FileType.pickType(fileName.substring(fileName.indexOf("."),fileName.length()).toLowerCase(Locale.ROOT)));
            f.setType(file.getContentType());
            f.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString());
            if(floder!=null)
                f.setFather(floder.getId());


            fileMapper.insertSelective(f) ;

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(FAILED_FILE_CREATE);
        }
        return new Result();
    }

    @Override
    public Result createFloder(long userId, String folderName, File currFolder) {
        try {
            OSSUtil ossUtil = new OSSUtil(endpoint, accessKeyId, accessKeySecret, bucketName, uploadPath);
            File file = new File();
            String ossPath = currFolder.getPath() + folderName + java.io.File.separator;
            Example pathExample = new Example(File.class) ;
            Example.Criteria  pathCriteria = pathExample.createCriteria() ;
            pathCriteria.andEqualTo("path",ossPath);

            if(fileMapper.selectOneByExample(pathExample)!=null)
                return new Result(FAILED_FILE_FLODER_EXIST);

            if (!ossUtil.createFolder(ossPath).equals("")) {
                // File file = new File() ;
                file.setId(UUIDUtil.UUID());
                file.setName(folderName);
                file.setPath(currFolder.getPath() + folderName + java.io.File.separator);
                file.setFather(currFolder.getId());
                file.setUserId(userId);
                file.setType(Constants.FILE_TYPE_FOLDER);
                fileMapper.insertSelective(file);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(FAILED_FILE_FLODER_CREATE);
        }

        return new Result(SUCCESS);
    }

    @Override
    public Result delFile(String fileId) {
//        if(fileId.equals(""))
//            throw  new BaseException("-1",不能删除跟目录)
        removeFiles(fileId) ;

        return new Result(SUCCESS);
    }

    @Override
    public Result copyFile(String sourceFileId, String toFolderId) {
        OSSUtil ossUtil = new OSSUtil(endpoint,accessKeyId,accessKeySecret,bucketName,uploadPath) ;

        File sourceFile = fileMapper.selectByPrimaryKey(sourceFileId) ;
        File toFloder = fileMapper.selectByPrimaryKey(toFolderId) ;
        String oldFilePath = sourceFile.getPath() ;
        String newFilePath = toFloder.getPath() ;
        if(sourceFile!=null&&toFloder!=null)
        {
            if(!sourceFile.getType().equals(Constants.FILE_TYPE_FOLDER)){
                ossUtil.copy(oldFilePath,newFilePath+sourceFile.getName());
                File newFile = new File() ;
                newFile.setId(UUIDUtil.UUID());
                newFile.setType(sourceFile.getType());
                newFile.setName(sourceFile.getName());
                newFile.setUserId(sourceFile.getUserId());
                newFile.setFather(toFloder.getId());
                newFile.setPath(newFilePath+sourceFile.getName());
                fileMapper.insertSelective(newFile) ;
            }else{
                //列出待拷贝的文件
                Example fe = new Example(File.class) ;
                Example.Criteria cri  = fe.createCriteria() ;
                cri.andEqualTo("father",sourceFileId) ;
                List<File> childs = fileMapper.selectByExample(fe) ;
                //创建新的文件夹
                File newDir = new File();
                newDir.setId(UUIDUtil.UUID());
                newDir.setName(sourceFile.getName());
                newDir.setType(sourceFile.getType());
                newDir.setFather(toFolderId);
                newDir.setUserId(sourceFile.getUserId());
                newDir.setPath(newFilePath+sourceFile.getName()+ java.io.File.separator);
                fileMapper.insertSelective(newDir) ;
                for (File c:childs
                     ) {
                    copyFile(c.getId(),newDir.getId()) ;
                }
            }
        }


        return new Result(SUCCESS);
    }

    @Override
    public Result copyFile(List<String> sourceFileIds, String toFolderId) {
        for (String sFileId: sourceFileIds
             ) {
           File  sFile =  fileMapper.selectByPrimaryKey(sFileId) ;
           if(sFile!=null){
                copyFile(sFileId,toFolderId);
           }
        }
        return new Result(SUCCESS);
    }

    @Override
    public Result cutFile(String sourceFileId, String toFolderId) {
        copyFile(sourceFileId,toFolderId) ;
        delFile(sourceFileId);
        return new Result(SUCCESS);
    }

    @Override
    public Result cutFile(List<String> sourceFileIds, String toFolderId) {
        copyFile(sourceFileIds,toFolderId) ;
        for (String sFileId:sourceFileIds
             ) {
            delFile(sFileId);
        }
        return new Result(SUCCESS);
    }

    @Override
    public Result listFile(String folderId,long userId,int page,int limit) {
        PageHelper.startPage(page,limit) ;
        Example e = new Example(File.class) ;
        Example.Criteria c = e.createCriteria() ;

        if(folderId.equals("")){
            Example e1 = new Example(File.class) ;
            Example.Criteria c1 = e1.createCriteria() ;
            c1.andEqualTo("userId",userId) ;
            c1.andIsNull("father") ;
            File file =  fileMapper.selectOneByExample(e1) ;
            folderId = file.getId() ;
        }
        c.andEqualTo("father",folderId) ;
        c.andEqualTo("userId",userId);
        e.setOrderByClause("type asc"); //
        List<File> files =  fileMapper.selectByExample(e) ;
//            int a = 0 ;
//            int b = 5/0 ;
        OSSUtil ossUtil = new OSSUtil(endpoint,accessKeyId,accessKeySecret,bucketName,uploadPath) ;
        for (File file:files
             ) {
            if(file.getType().split("/")[0].equals(Constants.FILE_TYPE_PHOTO)){
                file.setPreUrl(ossUtil.getUrl(file.getPath(),"fixed",80,80)); ; //缩略图
                file.setUrl(ossUtil.getUrl(file.getPath(),"",0,0)) ; //原图
            }
        }
        PageInfo<File> info = new PageInfo<>(files) ;
        System.out.println("totalCount: "+info.getTotal());
            return new Result(files,info.getTotal());
    }

    @Override
    public Result getRootFloder(long userId) {
        Example e = new Example(File.class) ;
        Example.Criteria c = e.createCriteria() ;
        c.andEqualTo("userId",userId)  ;
        c.andIsNull("father");
        File file=  fileMapper.selectOneByExample(e) ;
        return new Result(file);
    }

    @Override
    public Result rename(String currFileId,String fileId,long userId, String newName) {
        if("".equals(currFileId)||"".equals(fileId)){
            return new Result(FAILED_FILE_FLODER_ID_EMPTY);
        }
        Example e = new Example(File.class) ;
        Example.Criteria c = e.createCriteria() ;
        c.andEqualTo("father",currFileId) ;
        c.andEqualTo("userId",userId) ;
        c.andEqualTo("name",newName) ;
        List<File> files =  fileMapper.selectByExample(e);
        if(files.size()>0){
            //当前目录已经存在文件名相同的文件
            return new Result(FAILED_FILE_EXIST) ;
        }else{
            //更新新文件名
            Example e1 = new Example(File.class) ;
            Example.Criteria c1 = e1.createCriteria() ;
            c1.andEqualTo("id",fileId) ;
            File newFile = new File() ;
            newFile.setName(newName);
            fileMapper.updateByExampleSelective(newFile,e1);
        }
        return new Result(SUCCESS);
    }

    @Override
    public Result zip(long userId, List<String> ids, HttpServletResponse response) {
        OutputStream out = null ;
        FileInputStream fis = null ;
        OSS oss = null ;


        try{

            //查询所选文件内容
            Example e = new Example(File.class) ;
            Example.Criteria c= e.createCriteria() ;
            for (String id: ids
                 ) {
                c.orEqualTo("id",id) ;
            }
            List<File> toZipFiles  =  fileMapper.selectByExample(e) ;

            //查询父文件夹作为压缩文件名
            Example e1 = new Example(File.class) ;
            Example.Criteria c1= e1.createCriteria() ;
            c1.andEqualTo("id",toZipFiles.get(0).getFather()) ;
            File father = fileMapper.selectOneByExample(e1);


        String folderPath = "root/"+userId+"/files/";
       // String zipName  ="testZip" ;
        OSSUtil ossUtil = new OSSUtil(endpoint, accessKeyId, accessKeySecret, bucketName, uploadPath);
        //  ossUtil.deleteFile(bucketName,"root/1/files/two/");
         oss = ossUtil.getOSSClient();
        //  CopyObjectResult result = oss.copyObject(bucketName, "root/1/files/one/", bucketName, "root/1/files/four/");
        java.io.File zipFile = java.io.File.createTempFile("abc", ".zip");
        FileOutputStream f = new FileOutputStream(zipFile);
        /**
         * 作用是为任何OutputStream产生校验和
         * 第一个参数是制定产生校验和的输出流，第二个参数是指定Checksum的类型 （Adler32（较快）和CRC32两种）
         */
        CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
        // 用于将数据压缩成Zip文件格式
        ZipOutputStream zos = new ZipOutputStream(csum);

        //listFile("root/1/files");
            for (File file:toZipFiles
                 ) {


        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setPrefix(file.getPath());  //指定下一级文件
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

            }
            zos.close();
            fis = new FileInputStream(zipFile);
        //重新上传到OSS里 暂时不要了
            oss.putObject(bucketName, "root/"+userId+"/zip/" + (father.getName().equals("/")?"打包文件":father.getName())+ ".zip", fis);
            oss.shutdown();


        //数据下载
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            int len = 0 ;
            while((len = fis.read(buffer))>0){
                out.write(buffer,0,len);
            }



            // 删除临时文件
            zipFile.delete();

        }catch (Exception e){
            e.printStackTrace();
            return new Result(FAILED_FILE_ZIP_ERROR);
        }finally {
            try {
                // 关闭流
                if (out != null) {
                    out.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (oss != null) {
                    oss.shutdown();
                }
            }catch (Exception e){
                e.printStackTrace();
                return new Result(FAILED_FILE_ZIP_ERROR);
            }
        }
        return new Result(SUCCESS);
    }

    private void removeFiles(String myId){
        OSSUtil ossUtil = new OSSUtil(endpoint,accessKeyId,accessKeySecret,bucketName,uploadPath) ;
        File myFile = fileMapper.selectByPrimaryKey(myId) ;

        if(myFile!=null){
            if(myFile.getType()!=""){
                if(!myFile.getType().equals(Constants.FILE_TYPE_FOLDER)){
                    //如果不是文件夹类型，直接删除
                    fileMapper.deleteByPrimaryKey(myId) ;
                    //OSSUtil ossUtil = new OSSUtil(endpoint,accessKeyId,accessKeySecret,bucketName,uploadPath) ;
                    ossUtil.deleteFile(bucketName,myFile.getPath());
                }else{
                    //如果当前文件是文件夹时，就递归删除此文件夹下所有的文件
                    //查找以myid的child
                    Example fe = new Example(File.class) ;
                    Example.Criteria cri = fe.createCriteria() ;
                    cri.andEqualTo("father",myId) ;
                    List<File> childs = fileMapper.selectByExample(fe) ;
                    for (File c:childs
                    ) {
                        if(c!=null&&c.getType()!="")
                            removeFiles(c.getId());
                    }
                    //最后删除自己节点
                    fileMapper.deleteByPrimaryKey(myId) ;
                }
                //删除文件
                ossUtil.deleteFile(bucketName,myFile.getPath());
            }
        }
    }
}
