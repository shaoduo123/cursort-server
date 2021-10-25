package com.shao.cursort.service;

import com.shao.cursort.exception.BaseException;
import com.shao.cursort.pojo.User;
import com.shao.cursort.result.Result;

public interface UserService {
    public Result login(String userName ,String passWord) throws BaseException;
    public Result loginByPhone(String phone,String captcha) ;
    public Result logout(long userId) ;
    public Result register(User user,String ca) ;
    public Result changePassword(String captcha,String mobile,long userId,String password);
    public Result changeProfileInfo(User user) ;
    public Result sendCaptcha(String phone) ;

}
