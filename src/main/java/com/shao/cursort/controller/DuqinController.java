package com.shao.cursort.controller;

import com.shao.cursort.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class DuqinController {

    @RequestMapping (value = "/duqin/getSongs",method = RequestMethod.GET)
    public @ResponseBody
    Result getSongs () {
        List<Map<String,Object>> songs = new ArrayList<>() ;
        Map<String,Object> song1 = new HashMap<>() ;
        Map<String,Object> song2 = new HashMap<>() ;
        Map<String,Object> song3 = new HashMap<>() ;
        Map<String,Object> song4 = new HashMap<>() ;
        Map<String,Object> song5 = new HashMap<>() ;
        Map<String,Object> song6 = new HashMap<>() ;
        Map<String,Object> song7 = new HashMap<>() ;

        song1.put("id",1);
        song1.put("userId",1);
        song1.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song1.put("songUrl","http://aaaaa.com");
        song1.put("cnName","萌萌细雨");
        song1.put("enName","萌萌的细雨嗷嗷");

        song2.put("id",2);
        song2.put("userId",2);
        song2.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song2.put("songUrl","http://aaaaa.com");
        song2.put("cnName","萌萌细雨");
        song2.put("enName","萌萌的细雨嗷嗷");

        song3.put("id",3);
        song3.put("userId",3);
        song3.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song3.put("songUrl","http://aaaaa.com");
        song3.put("cnName","萌萌细雨");
        song3.put("enName","萌萌的细雨嗷嗷");

        song3.put("id",3);
        song3.put("userId",3);
        song3.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song3.put("songUrl","http://aaaaa.com");
        song3.put("cnName","萌萌细雨");
        song3.put("enName","萌萌的细雨嗷嗷");

        song4.put("id",4);
        song4.put("userId",4);
        song4.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song4.put("songUrl","http://aaaaa.com");
        song4.put("cnName","萌萌细雨");
        song4.put("enName","萌萌的细雨嗷嗷");

        song5.put("id",5);
        song5.put("userId",5);
        song5.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song5.put("songUrl","http://aaaaa.com");
        song5.put("cnName","萌萌细雨");
        song5.put("enName","萌萌的细雨嗷嗷");

        song6.put("id",6);
        song6.put("userId",6);
        song6.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song6.put("songUrl","http://aaaaa.com");
        song6.put("cnName","萌萌细雨");
        song6.put("enName","萌萌的细雨嗷嗷");

        song7.put("id",7);
        song7.put("userId",7);
        song7.put("coverPictureUrl","https://upload.jianshu.io/users/upload_avatars/13222938/638d9891-e387-43b1-aaba-62189c0caad7.jpg");
        song7.put("songUrl","http://aaaaa.com");
        song7.put("cnName","萌萌细雨");
        song7.put("enName","萌萌的细雨嗷嗷");

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);
        songs.add(song6);
        songs.add(song7);

        return new Result(songs);

    }

}
