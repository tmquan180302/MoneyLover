package com.poly.moneylover.models.Request;

public class ReqRegister {
    private String email;
    private String passWord;
    private String fullName;
    private int phone;

    public ReqRegister(String email, String passWord, String fullName) {
        this.email = email;
        this.passWord = passWord;
        this.fullName = fullName;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
