package com.poly.moneylover.models.Request;

import com.google.gson.annotations.SerializedName;

public class ServerReqLogin {
    @SerializedName("_id")
    private String _id;
    String email, passWord;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getUserId() {
        return _id;
    }

    public void setUserId(String userId) {
        this._id = userId;
    }

}
