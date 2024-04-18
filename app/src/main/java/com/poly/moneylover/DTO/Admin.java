package com.poly.moneylover.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Admin")
public class Admin {
    @PrimaryKey(autoGenerate = true)
    private int id_admin;
    private String email;
    private String matkhau;
    private String hoten;
    private String tensan;
    private String diachi;

    public Admin() {
    }

    public Admin(String email, String matkhau, String hoten, String tensan, String diachi) {
        this.email = email;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.tensan = tensan;
        this.diachi = diachi;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTensan() {
        return tensan;
    }

    public void setTensan(String tensan) {
        this.tensan = tensan;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}