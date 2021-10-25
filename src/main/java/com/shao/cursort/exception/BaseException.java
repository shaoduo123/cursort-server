package com.shao.cursort.exception;

import com.shao.cursort.result.ResultStatus;

public class BaseException extends RuntimeException{
    private int code ;
    private String info ;

    public BaseException(){
        this.code = 0 ;
        this.info = "success" ;
    }
    public BaseException(int code, String info){
        super(info);
        this.code = code ;
        this.info = info ;
    }

    public BaseException(ResultStatus rs) {
        code = rs.getCode() ;
        info = rs.getMsg() ;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
