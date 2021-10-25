package com.shao.cursort.controller;

import com.shao.cursort.annotation.CurrentFolder;
import com.shao.cursort.pojo.File;
import com.shao.cursort.pojo.User;
import com.shao.cursort.result.Result;
import com.shao.cursort.service.FileService;
import com.shao.cursort.service.MobFileService;
import com.shao.cursort.token.Authorization;
import com.shao.cursort.token.CurrentUser;
import com.shao.cursort.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.shao.cursort.result.ResultStatus.SUCCESS;

@Controller
@RequestMapping(value ="/file")
public class FileController {



    @Autowired
    private FileService fileService ;





    /**
     * 文件名长度
     */
    private static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_EXTENSIONS = {
            "jpg", "img", "png", "gif"
    };


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @Authorization
    public @ResponseBody
    Result upload(@RequestParam("file") MultipartFile file, @CurrentUser User user, @CurrentFolder File folder){

        return  fileService.upload(user.getId(),file,folder) ;
    }

    @RequestMapping(value = "/createFolder",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result createFloder(@CurrentUser User user, @CurrentFolder File currFolder,String folderName){

        return  fileService.createFloder(user.getId(),folderName,currFolder);
    }

    @RequestMapping(value = "/rename",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result rename(@CurrentUser User user, @CurrentFolder File currFile,String fileId, String newName){

        return  fileService.rename(currFile.getId(),fileId,user.getId(),newName) ;
    }


    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result del(String fileId){
        return  fileService.delFile(fileId);
    }

    @RequestMapping(value = "/dels",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result dels(String  ids){
        String ids1 [] = ids.split(",");

        for (String id:ids1
             ) {
            fileService.delFile(id);
        }
        return new Result();
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result list(@CurrentUser User user , String fileId,int page,int count,String keyword ){
        return  fileService.listFile(fileId,user.getId(),page,count) ;
    }

//    @RequestMapping(value = "/listCurr",method = RequestMethod.GET)
//    @Authorization
//    public @ResponseBody
//    Result listCurrFloder(@CurrentFolder File currFloder,int page,int count ){
//        return  fileService.listFile(currFloder.getId(),page,count) ;
//    }

    @RequestMapping(value = "/getRoot",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result getRoot(@CurrentUser User user,@CurrentFolder File file){
        return  fileService.getRootFloder(user.getId()) ;
    }


    @RequestMapping(value = "/copy",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result copy(String sourceFileId,String toFolderId){
        return  fileService.copyFile(sourceFileId,toFolderId);
    }



    @RequestMapping(value = "/copys",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result copys(String sourceFileIds,String toFolderId){
        String sourceFileIdArray [] = sourceFileIds.split(",");
        for (String sourceFileId: sourceFileIdArray
             ) {
            fileService.copyFile(sourceFileId,toFolderId);
        }

        return  new Result();
    }



    @RequestMapping(value = "/cuts",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result cuts(String sourceFileIds,String toFolderId){
        String sourceFileIdArray [] = sourceFileIds.split(",");
        for (String sourceFileId: sourceFileIdArray
        ) {
            fileService.copyFile(sourceFileId,toFolderId);
            fileService.delFile(sourceFileId);
        }

        return  new Result();
    }







//    @RequestMapping(value = "/upload",method = RequestMethod.POST)
//    @Authorization
//    public @ResponseBody
//    Result upload1(@RequestParam("file") MultipartFile file, @CurrentUser User user){
//        //fileService.upload(user.getId(),file,folder) ;
//        System.out.println("fileupload");
//        return  new Result() ;
//    }


}
