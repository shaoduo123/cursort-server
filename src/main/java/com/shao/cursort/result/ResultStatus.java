package com.shao.cursort.result;

/**
 * 状态码
 */
public enum ResultStatus {

    SUCCESS(0,"成功"),
    FAILED(1, "错误"),
    ERROR(-1,"错误"),
    USER_PASS_ERROR(2,"用户名或密码错误"),
    USER_LOGOUT_SUCCESS(3,"退出成功！"),
    USER_LOGOUT_FAILED(4,"退出失败！"),
    FAILED_USER_EXIST(5,"用户名已经存在"),
    FAILED_USER_PHONE_EXIST(6,"手机号已经存在"),
    FAILED_USER_MAIL_EXIST(7,"邮箱已经存在"),
    FAILED_CAPTCHA_SEND_REPEAT(8,"请勿重复发送短信验证码"),
    FAILED_CAPTCHA_SEND_MATCH(9,"短信验证码输入错误"),
    FAILED_CAPTCHA_SEND_NOT_EXIST(10,"短信验证码不存在"),
    FAILED_FILE_UPLOAD_EMPTY(12,"文件不能为空"),
    FAILED_FILE_CREATE(14,"文件上传失败"),
    FAILED_FILE_FLODER_CREATE(13,"文件夹创建失败"),
    FAILED_FILE_FLODER_EXIST(15,"文件已经存在，请更换名称"),
    FAILED_FILE_EXIST(16,"文件已经存在"),
    FAILED_FILE_FLODER_NAME_EMPTY(17,"文件名不能为空"),

    FAILED_NOT_LOGIN(401,"您还未登陆请先登陆"),

    SUCCESS_CAPTCHA_SEND(11,"验证码发送成功");

    private int code;
    private String msg;
    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

}
