package com.shao.cursort.interceptor;

import com.shao.cursort.annotation.CurrentFolder;
import com.shao.cursort.mapper.FileMapper;
import com.shao.cursort.mapper.UserMapper;
import com.shao.cursort.pojo.File;
import com.shao.cursort.pojo.User;
import com.shao.cursort.token.CurrentUser;
import com.shao.cursort.utils.Constants;
import com.shao.cursort.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import tk.mybatis.mapper.entity.Example;

import static com.shao.cursort.utils.Constants.FILE_TYPE_FOLDER;


public class CurrentoFolderMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private FileMapper fileMapper;

    @Value("${oss.upload-path}")
    private String uploadPath ;

    @Override
    public boolean supportsParameter (MethodParameter parameter) {
        // 如果参数类型是 User 并且有 CurrentUser 注解则支持
        if (parameter.getParameterType ().isAssignableFrom (File.class) &&
                parameter.hasParameterAnnotation (CurrentFolder.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument (MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        System.out.println("当前所处文件夹");
        String currenFolderId = (String) webRequest.getAttribute (Constants.CURRENT_FOLDER_ID, RequestAttributes.SCOPE_REQUEST);
        long userId = (long) webRequest.getAttribute (Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);

        //定义默认的用户目录
        String defaultPath = uploadPath+ userId+ java.io.File.separator+"files"+java.io.File.separator;

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
            fileMapper.insertSelective(file);
        }


        File currFolder = null ;
        if(currenFolderId==null || currenFolderId.equals("")){
            //默认拿根目录的folder
            Example currFileExample = new Example(File.class) ;
            Example.Criteria currFolderCri = currFileExample.createCriteria() ;
            currFolderCri.andEqualTo("userId",userId) ;
            currFolderCri.andIsNull("father");
            currFolder =  fileMapper.selectOneByExample(currFileExample);
        }else{
             currFolder = fileMapper.selectByPrimaryKey(currenFolderId);
        }

        //如果当前文件不是文件夹，就取其父亲地址
        if(currFolder!=null && !currFolder.getType().equals(FILE_TYPE_FOLDER)){
            Example fe = new Example(File.class) ;
            Example.Criteria criteria = fe.createCriteria() ;
            criteria.andEqualTo("id",currFolder.getFather()) ;
            currFolder = fileMapper.selectOneByExample(fe) ; //将父亲文件夹信息赋给currFolder
        }
        return currFolder ;

            //throw new MissingServletRequestPartException(Constants.CURRENT_FOLDER_ID);
    }
}