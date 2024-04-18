package com.poly.moneylover.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "DichVu")
public class DichVu {
    @PrimaryKey(autoGenerate = true)
    int id_dv;
    String tendv;
    String giadv;

    public String getTendv() {
        return tendv;
    }

    public DichVu() {
    }

    public void setTendv(String tendv) {
        this.tendv = tendv;
    }

    public DichVu(String tendv, String giadv) {
        this.tendv = tendv;
        this.giadv = giadv;
    }

    public int getId_dv() {
        return id_dv;
    }

    public void setId_dv(int id_dv) {
        this.id_dv = id_dv;
    }

    public String getGiadv() {
        return giadv;
    }

    public void setGiadv(String giadv) {
        this.giadv = giadv;
    }
}



