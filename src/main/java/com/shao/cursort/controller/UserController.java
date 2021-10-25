package com.shao.cursort.controller;

import com.shao.cursort.token.Authorization;
import com.shao.cursort.token.CurrentUser;
import com.shao.cursort.token.TokenManager;
import com.shao.cursort.exception.BaseException;
import com.shao.cursort.pojo.User;
import com.shao.cursort.result.Result;
import com.shao.cursort.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.Assert;

import java.util.Map;

@Controller
@RequestMapping(value = "/tokens")
public class UserController {
    /**
     * 获取和删除 token 的请求地址，在 Restful 设计中其实就对应着登录和退出登录的资源映射
     * @author ScienJus
     * @date 2015/7/30.
     */

        @Autowired
        private UserService userService;

        @Autowired
        private TokenManager tokenManager;

        @RequestMapping (value = "/login",method = RequestMethod.POST)
        public @ResponseBody  Result login (@RequestBody Map map) {
            Assert.notNull (map.get("username"), "username can not be empty");
            Assert.notNull (map.get("password"), "password can not be empty");
            try{
                return  userService.login(map.get("username").toString(),map.get("password").toString()) ;
            }catch (BaseException e){
                return new Result(e.getCode(),e.getInfo());
            }
        }

        @RequestMapping (value = "/loginByPhone",method = RequestMethod.POST)
        public @ResponseBody  Result loginByPhone (@RequestBody Map map) {
            Assert.notNull (map.get("phone"), "username can not be empty");
            Assert.notNull (map.get("captcha"), "password can not be empty");
            return  userService.loginByPhone(map.get("phone").toString(),map.get("captcha").toString());

        }

        @RequestMapping (value = "/logout",method = RequestMethod.POST)
        @Authorization //需要用户的权限就加上authorization
        public @ResponseBody Result logout (@CurrentUser User user) {
          return userService.logout(user.getId()) ;
        }


        @RequestMapping (value = "/register",method = RequestMethod.POST)
        public @ResponseBody Result register (@RequestBody Map map) {
            User user = new User();
            Assert.notNull (map.get("phone"), "phone can not be empty");
            Assert.notNull (map.get("password"), "password can not be empty");
            Assert.notNull (map.get("captcha"), "captcha can not be empty");
            user.setName(map.get("phone").toString());
            user.setPhone(map.get("phone").toString());
            user.setPassword(map.get("password").toString());
            return userService.register(user,map.get("captcha").toString()) ;
        }

        @RequestMapping (value = "/sendCaptcha",method = RequestMethod.GET)
        public @ResponseBody Result sendCaptcha (String phone) {
            return userService.sendCaptcha(phone) ;
        }

        @RequestMapping (value = "/changeProfileInfo",method = RequestMethod.POST)
        @Authorization //需要用户的权限就加上authorization
        public @ResponseBody Result changeProfileInfo (@CurrentUser User user,@RequestBody User userInfo) {
            userInfo.setId(user.getId());
            return userService.changeProfileInfo(userInfo) ;
        }

        @RequestMapping (value = "/changePwd",method = RequestMethod.POST)
        @Authorization //需要用户的权限就加上authorization
        public @ResponseBody Result changePwd (@CurrentUser User user,@RequestParam String password,@RequestParam String captcha  ) {
            return userService.changePassword(captcha,user.getPhone(),user.getId(),password) ;
        }


        @RequestMapping (value = "/a",method = RequestMethod.POST)
        public @ResponseBody Result a () {
            return new Result() ;
        }


}
