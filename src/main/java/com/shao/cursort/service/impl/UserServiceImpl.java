package com.shao.cursort.service.impl;

import com.shao.cursort.token.TokenManager;
import com.shao.cursort.token.TokenModel;
import com.shao.cursort.exception.BaseException;
import com.shao.cursort.mapper.UserMapper;
import com.shao.cursort.pojo.User;
import com.shao.cursort.result.Result;
import com.shao.cursort.result.ResultStatus;
import com.shao.cursort.service.UserService;
import com.shao.cursort.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.shao.cursort.result.ResultStatus.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper ;

    @Autowired
    private TokenManager tokenManager ;

    @Autowired
    RedisTemplate redisTemplate ;


    @Override
    public Result login(String userName, String passWord) throws BaseException {
        User user = userMapper.getUserByNameOrPhone(userName) ;
        if (user == null || // 未注册
                !user.getPassword().equals (passWord)) { // 密码错误
            // 提示用户名或密码错误
            throw new BaseException(ResultStatus.USER_PASS_ERROR);
        }
        // 生成一个 token，保存用户登录状态
        TokenModel model = tokenManager.createToken (user.getId());
        user.setTokens(user.getId()+"_"+model.getToken());
        user.setPassword("");

        //return new ResponseEntity (ResultModel.ok (model), HttpStatus.OK);
        return new Result(SUCCESS,user) ;
    }

    @Override
    public Result loginByPhone(String phone, String captcha) {
        if(redisTemplate.hasKey(phone)){
            if(redisTemplate.boundValueOps(phone).get().equals(captcha)) {
                    User user = userMapper.getUserByPhone(phone) ;
                    TokenModel model = tokenManager.createToken (user.getId());
                    user.setTokens(user.getId()+"_"+model.getToken());
                    user.setPassword("");
                    return new Result(SUCCESS,user) ;
                }else{
                return new Result(FAILED) ;
                }
            }
        return new Result(FAILED) ;
    }

    @Override
    public Result logout(long userId) {
        tokenManager.deleteToken (userId);
        return new Result(ResultStatus.USER_LOGOUT_SUCCESS) ;
    }

    @Override
    public Result register(User user,String captcha) {
        if(!redisTemplate.hasKey(user.getPhone())) {
            return new Result(ResultStatus.FAILED_CAPTCHA_SEND_MATCH) ;
        }else if(!redisTemplate.boundValueOps(user.getPhone()).get().equals(captcha)) {
            return  new Result(ResultStatus.FAILED_CAPTCHA_SEND_MATCH) ;
        }

        Example  userExample = new Example(User.class) ;
        Example.Criteria c =  userExample.createCriteria() ;
        c.orEqualTo("name",user.getName());
        c.orEqualTo("phone",user.getPhone());
        List<User> users = userMapper.selectByExample(userExample) ;

      if(users.size()>0){

          //遍历检查是否 用户名、手机号、邮箱不存在
          for (User u:users
               ) {
              if(u.getName().equals(user.getName())){
                  return new Result(ResultStatus.FAILED_USER_EXIST) ;
              }else if(u.getPhone().equals(user.getPhone())){
                  return new Result(ResultStatus.FAILED_USER_PHONE_EXIST) ;
              }else if(u.getMail().equals(user.getMail())){
                  return new Result(ResultStatus.FAILED_USER_MAIL_EXIST) ;
              }
          }
      }


        user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ").format(new Date()));
        user.setBalance(0+"");
        userMapper.insert(user) ;
        return new Result(SUCCESS);
    }


    @Override
    public Result changePassword(String captcha,String mobile, long userId,String password) {
        if(redisTemplate.hasKey(mobile)){
            if(redisTemplate.boundValueOps(mobile).get().equals(captcha)){
                User user = new User() ;
                user.setId(userId);
                user.setPassword(password);
                try {
                    userMapper.updateByPrimaryKey(user);
                }catch (Exception e){
                    return new Result(FAILED) ;
                }
            }else{
                return  new Result(FAILED_CAPTCHA_SEND_MATCH) ;
            }
        }else
            return  new Result(FAILED_CAPTCHA_SEND_NOT_EXIST);
        return new Result(SUCCESS);
    }

    @Override
    public Result changeProfileInfo(User user) {
        try{
            //userMapper.updateByPrimaryKey(user) ;
            int i = userMapper.updateByPrimaryKeySelective(user) ;
            System.out.println(i);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(FAILED) ;
        }
        return new Result(SUCCESS);
    }

    @Override
    public Result sendCaptcha(String mobile) {
        //判断是否发过验证码
        if(redisTemplate.hasKey(mobile)){
            return new Result(FAILED_CAPTCHA_SEND_REPEAT);
        }
        //生成验证吗
        String captcha = CaptchaUtil.generateCaptcha();
        //调用发送短信接口
        System.out.println("生成验证码:"+captcha);
        boolean ok = true ; //此位置预留短信接口发送状态，短信发送成功状态
        if(ok)
            redisTemplate.boundValueOps(mobile).set(captcha,90, TimeUnit.SECONDS);
        else
            return new Result(SUCCESS_CAPTCHA_SEND);
        return new Result(SUCCESS);
    }


}
