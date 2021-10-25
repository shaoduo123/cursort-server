package com.shao.cursort.token;

/**
 * Token 的 Model 类，可以增加字段提高安全性，例如时间戳、url 签名
 */
public class TokenModel {

    // 用户 id
    private long userId;

    // 随机生成的 uuid
    private String token;

    public TokenModel (long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId () {
        return userId;
    }

    public void setUserId (long userId) {
        this.userId = userId;
    }

    public String getToken () {
        return token;
    }

    public void setToken (String token) {
        this.token = token;
    }
}