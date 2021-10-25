package com.shao.cursort.utils;

import java.util.UUID;

public  class UUIDUtil{
    public static String UUID(){
        String uuid =  UUID.randomUUID().toString() ;
        uuid = uuid.replace("-", "");
        return uuid ;
    }
}
