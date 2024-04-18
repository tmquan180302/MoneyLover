package com.poly.moneylover.DTO;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LoaiSan")
public class LoaiSan {
    @PrimaryKey(autoGenerate = true)
    int id_loaisan;
    String tenloai;


    public LoaiSan() {
    }

    public LoaiSan(String tenloai) {
        this.tenloai = tenloai;
    }

    public int getId_loaisan() {
        return id_loaisan;
    }

    public void setId_loaisan(int id_loaisan) {
        this.id_loaisan = id_loaisan;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}

