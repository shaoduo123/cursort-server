package com.shao.cursort.result;

/**
 * 返回结果
 */
public class Result {
    private int code ;
    private String msg ;
    private Object data ;
    private long total ;

    public Result(){
        this.code = 0 ;
        this.msg = "成功" ;
    }

    public Result(Object data){
        this.code = 0 ;
        this.msg = "成功" ;
        this.data = data ;
    }
    public Result(Object data,long total){
        this.code = 0 ;
        this.msg = "成功" ;
        this.data = data ;
        this.total = total ;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Result(int code, String msg,Object data,int total) {
        this.code = code;
        this.msg = msg;
        this.data = data ;
        this.total = total ;
    }

    public Result(ResultStatus rs){
        this.code = rs.getCode() ;
        this.msg = rs.getMsg() ;
    }

    public Result(ResultStatus rs,Object data){
        this.code = rs.getCode() ;
        this.msg = rs.getMsg() ;
        this.data = data ;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
