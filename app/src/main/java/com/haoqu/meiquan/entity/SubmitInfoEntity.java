package com.haoqu.meiquan.entity;

/**
 * Created by apple on 16/7/29.
 */
public class SubmitInfoEntity {

    private String error;
    private String nickname;


    public SubmitInfoEntity(String error, String nickname) {
        this.error = error;
        this.nickname = nickname;
    }

    public SubmitInfoEntity() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "SubmitInfoEntity{" +
                "error='" + error + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
