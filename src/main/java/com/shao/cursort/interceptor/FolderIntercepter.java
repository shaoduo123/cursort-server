package com.shao.cursort.interceptor;

import com.shao.cursort.mapper.FileMapper;
import com.shao.cursort.pojo.File;
import com.shao.cursort.token.Authorization;
import com.shao.cursort.token.TokenManager;
import com.shao.cursort.token.TokenModel;
import com.shao.cursort.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.shao.cursort.utils.Constants.FILE_TYPE_FOLDER;

/**
 * 自定义拦截器，判断此次请求是否有权限
 */
@Component
public class FolderIntercepter implements HandlerInterceptor {


    @Autowired
    private FileMapper fileMapper ;

    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String currFolderId = request.getParameter(Constants.CURRENT_FILE_ID) ;
//        if(currFolderId==null||currFolderId.equals(""))
//            return  false ;

        request.setAttribute (Constants.CURRENT_FOLDER_ID, currFolderId);

        return true;
    }
}