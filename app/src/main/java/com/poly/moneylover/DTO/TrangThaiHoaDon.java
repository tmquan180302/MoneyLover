package com.poly.moneylover.DTO;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TrangThaiHoaDon")
public class TrangThaiHoaDon {
    @PrimaryKey(autoGenerate = true)
    int id_trangthaihd;
    String tentrangthai;

    public TrangThaiHoaDon() {
    }

    public TrangThaiHoaDon(int id_trangthaihd, String tentrangthai) {
        this.id_trangthaihd = id_trangthaihd;
        this.tentrangthai = tentrangthai;
    }

    public TrangThaiHoaDon(String tentrangthai) {
        this.tentrangthai = tentrangthai;
    }

    public int getId_trangthaihd() {
        return id_trangthaihd;
    }

    public void setId_trangthaihd(int id_trangthaihd) {
        this.id_trangthaihd = id_trangthaihd;
    }

    public String getTentrangthai() {
        return tentrangthai;
    }

    public void setTentrangthai(String tentrangthai) {
        this.tentrangthai = tentrangthai;
    }
}
