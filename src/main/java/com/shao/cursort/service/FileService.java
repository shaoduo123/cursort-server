package com.shao.cursort.service;

import com.shao.cursort.pojo.File;
import com.shao.cursort.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService {
    public String getIdByPath(String path) ;
    public Result upload(long userId, MultipartFile file, File currFloder);
    public Result createFloder(long userId ,String folderName,File currFolder) ;
    public Result delFile(String fileId) ;
    public Result copyFile(String sourceFileId,String toFolderId) ;
    public Result copyFile(List<String> sourceFileIds, String toFolderId) ;
    public Result cutFile(String sourceFileId ,String toFolderId) ;
    public Result cutFile(List<String> sourceFileIds ,String toFolderId) ;
    public Result listFile(String folderId,long userId,int page ,int limit ) ;
    public Result getRootFloder(long userId) ;
    public Result rename(String fatherId,String fileId,long userId,String name) ;
    public Result zip(long userId, List<String> ids, HttpServletResponse response) ;

}
