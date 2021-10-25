package com.shao.cursort.handle;

import com.shao.cursort.exception.BaseException;
import com.shao.cursort.result.Result;
import com.shao.cursort.result.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.shao.cursort.result.ResultStatus.ERROR;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof BaseException) {   //判断异常是否是我们定义的异常
            BaseException exception = (BaseException) e;
            //return girlException.getCode(), girlException.getMessage());
            return new Result(exception.getCode(),exception.getInfo());
        }else {
            System.out.println("【系统异常】{}"+ e);
            e.printStackTrace();
            return new Result(-1,"未知错误") ;
        }
    }
}
