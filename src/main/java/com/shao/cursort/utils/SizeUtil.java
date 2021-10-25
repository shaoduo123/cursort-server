package com.shao.cursort.utils;

import java.math.BigDecimal;

public class SizeUtil {
    public static String reSize(long size){
        Float realSize = Float.parseFloat(String.valueOf(size))/1024;  //最开始是b /1024改为 kb
        BigDecimal b = new BigDecimal(realSize);
        realSize = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        if(realSize<1024){
            return BigDecimal.valueOf(realSize).setScale(2,BigDecimal.ROUND_HALF_UP)+"KB";
        }else {
            if((realSize/1024)<1024){
                return BigDecimal.valueOf(realSize / 1024 ).setScale(2,BigDecimal.ROUND_HALF_UP)+"M";
            }else{
                if((realSize/1024/1024)<1024){
                    return new BigDecimal((realSize/1024/1024)).setScale(2,BigDecimal.ROUND_HALF_UP)+"G";
                }else{
                    return new BigDecimal((realSize/1024/1024/1024)).setScale(2,BigDecimal.ROUND_HALF_UP)+"T";
                }
            }
        }
    }
}
