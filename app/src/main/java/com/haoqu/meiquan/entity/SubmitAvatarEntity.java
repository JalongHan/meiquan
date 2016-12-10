package com.haoqu.meiquan.entity;

/**
 * 上传图片返回的entity
 * Created by apple on 16/7/29.
 */
public class SubmitAvatarEntity {
    private String error;
    private String avatar;

    public SubmitAvatarEntity() {
    }

    @Override
    public String toString() {
        return "SubmitAvatarEntity{" +
                "avatar='" + avatar + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public SubmitAvatarEntity(String avatar, String error) {
        this.avatar = avatar;
        this.error = error;
    }
}
