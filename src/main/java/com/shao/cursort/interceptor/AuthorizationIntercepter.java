package com.shao.cursort.interceptor;

import com.shao.cursort.exception.BaseException;
import com.shao.cursort.result.ResultStatus;
import com.shao.cursort.token.Authorization;
import com.shao.cursort.token.TokenManager;
import com.shao.cursort.token.TokenModel;
import com.shao.cursort.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 */
@Component
public class AuthorizationIntercepter implements HandlerInterceptor {

    @Autowired
    private TokenManager manager;


    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response, Object handler) throws Exception {
        System.out.println("wodaole");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod ();
        // 从 header 中得到 token
        String authorization = request.getHeader (Constants.AUTHORIZATION);
        // 验证 token
        TokenModel model = manager.getToken (authorization);
//        if(model==null){
//            return  false ;
//        }
        if (manager.checkToken (model)) {
            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
            request.setAttribute (Constants.CURRENT_USER_ID, model.getUserId ());
            return true;
        }
        // 如果验证 token 失败，并且方法注明了 Authorization，返回 401 错误
        if (method.getAnnotation (Authorization.class) != null) {
            response.setStatus (HttpServletResponse.SC_UNAUTHORIZED);
            throw new BaseException(ResultStatus.FAILED_NOT_LOGIN);
           // return false;
        }
        return true;
    }
}