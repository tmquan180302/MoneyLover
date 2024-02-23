package com.poly.moneylover.models;

import java.io.Serializable;

public class Dto_item   implements Serializable {

    private int id, danhmuc;

    private String  ghichu,tien,ngay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(int danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTien() {
        return tien;
    }

    public void setTien(String tien) {
        this.tien = tien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public Dto_item(int id, int danhmuc, String ghichu, String tien, String ngay) {
        this.id = id;
        this.danhmuc = danhmuc;
        this.ghichu = ghichu;
        this.tien = tien;
        this.ngay = ngay;
    }
}
