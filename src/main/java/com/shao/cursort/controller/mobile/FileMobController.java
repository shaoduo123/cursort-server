package com.shao.cursort.controller.mobile;

import com.shao.cursort.annotation.CurrentFolder;
import com.shao.cursort.mapper.FileMapper;
import com.shao.cursort.pojo.File;
import com.shao.cursort.pojo.User;
import com.shao.cursort.result.Result;
import com.shao.cursort.service.FileService;
import com.shao.cursort.service.MobFileService;
import com.shao.cursort.token.Authorization;
import com.shao.cursort.token.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.shao.cursort.result.ResultStatus.FAILED_FILE_FLODER_NAME_EMPTY;

@Controller
@RequestMapping(value ="/mobile/file")
public class FileMobController {



    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileService fileService ;

    @Autowired
    private MobFileService mobFileService ;



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
    Result upload(@RequestParam("file") MultipartFile file,@CurrentUser User user,@RequestParam("father") String  father){
        Example currFileExample = new Example(File.class) ;
        Example.Criteria currFolderCri = currFileExample.createCriteria() ;
        currFolderCri.andEqualTo("userId",user.getId()) ;

        if(("").equals(father)){
            currFolderCri.andIsNull("father");
        }else{
            currFolderCri.andEqualTo("father",father);
        }
        File folder =  fileMapper.selectOneByExample(currFileExample);
        return  fileService.upload(user.getId(),file,folder) ;
    }

    @RequestMapping(value = "/createFolder",method = RequestMethod.POST)
    @Authorization
    public @ResponseBody
    Result createFloder(@CurrentUser User user, @RequestBody Map map){
        Assert.notNull (map.get("father"), "father can not be empty");
        Assert.notNull (map.get("name"), "name can not be empty");
        Example currFileExample = new Example(File.class) ;
        Example.Criteria currFolderCri = currFileExample.createCriteria() ;
        currFolderCri.andEqualTo("userId",user.getId()) ;
        if(map.get("name") == null){
            return new Result(FAILED_FILE_FLODER_NAME_EMPTY) ;
        }else{
            if(map.get("name").toString().equals("")){
                return new Result(FAILED_FILE_FLODER_NAME_EMPTY) ;
            }
        }
        if(map.get("father")!=null){
            if(map.get("father").toString().equals("")){
                currFolderCri.andIsNull("father");
            }else{
                currFolderCri.andEqualTo("father",map.get("father").toString());
            }

        }else{
            currFolderCri.andIsNull("father");
        }

        File folder =  fileMapper.selectOneByExample(currFileExample);
        return  fileService.createFloder(user.getId(),map.get("name").toString(),folder);
    }

    @RequestMapping(value = "/rename",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result rename(@CurrentUser User user, String father,String fileId, String newName){

        return  fileService.rename(father,fileId,user.getId(),newName) ;
    }


    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result del(String fileId){
        return  fileService.delFile(fileId);
    }


    @RequestMapping(value = "/dels",method = RequestMethod.POST)
    @Authorization
    public @ResponseBody
    Result del1s(@RequestBody  Map map){
        List<String> ids  = (List<String>) map.get("ids");

        for (String id:ids
        ) {
            fileService.delFile(id);
        }
        return new Result();
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result list(@CurrentUser User user , String fileId,int page,int count ){
        return  mobFileService.listFile(fileId,user.getId(),page,count) ;
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @Authorization
    public @ResponseBody
    Result list(@CurrentUser User user , String fileId,int page,int count,String keyword ){
        return  mobFileService.listFile(fileId,user.getId(),page,count) ;
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





    @RequestMapping(value = "/copys",method = RequestMethod.POST)
    @Authorization
    public @ResponseBody
    Result copys(@RequestBody Map map){
        List<String> sourceFileIds = (List<String>) map.get("sourceFileIds");
        String toFolderId = map.get("toFolderId").toString() ;
        for (String sourceFileId: sourceFileIds
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


    @RequestMapping(value = "/zip",method = RequestMethod.POST)
    @Authorization
    public @ResponseBody
    Result zip(@CurrentUser User user, @RequestBody Map map, HttpServletResponse response){
        List<String> ids = (List<String>) map.get("fileIds");

        return fileService.zip(user.getId(),ids,response);

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
