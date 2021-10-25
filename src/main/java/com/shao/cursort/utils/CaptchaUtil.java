package com.shao.cursort.utils;

import java.util.UUID;

public class CaptchaUtil {
    public static int DEFAULT_NUM = 6 ;
    public static String generateCaptcha(){
        /*生成6位随机数*/
        String captcha = UUID.randomUUID().toString()
                .replaceAll("-","")
                .replaceAll("[a-z|A-Z]","")
                .substring(0, CaptchaUtil.DEFAULT_NUM);
        return  captcha ;
    }

    public static String generateCaptcha(int num){
        /*生成num位随机数*/
        String captcha = UUID.randomUUID().toString()
                .replaceAll("-","")
                .replaceAll("[a-z|A-Z]","")
                .substring(0, num);
        return  captcha ;
    }
}
