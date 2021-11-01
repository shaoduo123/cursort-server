package com.shao.cursort.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shao.cursort.mapper.FileMapper;
import com.shao.cursort.pojo.File;
import com.shao.cursort.result.Result;
import com.shao.cursort.service.MobFileService;
import com.shao.cursort.utils.Constants;
import com.shao.cursort.utils.OSSUtil;
import com.shao.cursort.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MobFileServiceImpl implements MobFileService {

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


    @Override
    public Result listFile(String folderId, long userId, int page, int limit) {
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
        Map<String,Object> result = new HashMap<>();
        List<Map<String,String>> maps = new ArrayList<>() ;
        for (File file:files
        ) {
            if(file.getType().split("/")[0].equals(Constants.FILE_TYPE_PHOTO)){
                file.setPreUrl(ossUtil.getUrl(file.getPath(),"fixed",80,80)); ; //缩略图
                file.setUrl(ossUtil.getUrl(file.getPath(),"",0,0)) ; //原图
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",file.getName()) ;
            map.put("amount",file.getSize()) ;
            map.put("fileId",file.getId()) ;
            map.put("preImg",file.getPreUrl()) ;
            map.put("img",file.getUrl());
            map.put("flag",file.getType().equals(Constants.FILE_TYPE_FOLDER)?"0":"1");
            maps.add(map);
        }

        result.put("directoryList",maps) ;

        PageInfo<File> info = new PageInfo<>(files) ;
        System.out.println("totalCount: "+info.getTotal());
        return new Result(result,info.getTotal());
    }

    @Override
    public boolean createRoot(long userId) {
        //定义默认的用户目录
        String defaultPath = uploadPath+ userId+ java.io.File.separator+"files"+java.io.File.separator;
        String currenFolderId = "" ;
        //查找该用户数据库中是否有目录记录，没有则创建，并默认为主目录
        Example folderExam = new Example(File.class);
        Example.Criteria folderCri = folderExam.createCriteria() ;
        folderCri.andEqualTo("userId",userId) ;
        if(fileMapper.selectByExample(folderExam).size()<=0 ){
            //没有主目录和任何文件
            currenFolderId = UUIDUtil.UUID() ;
            File file = new File();
            file.setPath(defaultPath);
            file.setName("/");
            file.setId(currenFolderId);
            file.setUserId(userId);
            file.setType(Constants.FILE_TYPE_FOLDER);
            file.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            fileMapper.insertSelective(file);
        }
        return  true ;
    }
}
