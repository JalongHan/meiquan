package com.haoqu.meiquan.entity;

/**
 * 账户的用户名和密码
 */
public class UserEntity {
    //手机号
    private String userPhoneNumber;
    //密码
    private String passWord;

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserEntity(String userPhoneNumber, String passWord) {
        super();
        this.userPhoneNumber = userPhoneNumber;
        this.passWord = passWord;
    }

    public UserEntity() {
        super();
    }

}
