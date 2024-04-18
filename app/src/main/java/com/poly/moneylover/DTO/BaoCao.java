package com.poly.moneylover.DTO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "BaoCao",foreignKeys = {
        @ForeignKey(entity = San.class, parentColumns = "id_san", childColumns = "id_san", onDelete = ForeignKey.CASCADE),
})
public class BaoCao {
    @PrimaryKey(autoGenerate = true)
    int id_bc;

    @ColumnInfo(name = "id_san")
    int id_san;
    String tensan;
    String giasan;

    String mota;


    public BaoCao( int id_san, String tensan, String giasan, String mota) {

        this.id_san = id_san;
        this.tensan = tensan;
        this.giasan = giasan;
        this.mota = mota;
    }

    public int getId_bc() {
        return id_bc;
    }

    public void setId_bc(int id_bc) {
        this.id_bc = id_bc;
    }



    public int getId_san() {
        return id_san;
    }

    public void setId_san(int id_san) {
        this.id_san = id_san;
    }

    public String getTensan() {
        return tensan;
    }

    public void setTensan(String tensan) {
        this.tensan = tensan;
    }

    public String getGiasan() {
        return giasan;
    }

    public void setGiasan(String giasan) {
        this.giasan = giasan;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}




