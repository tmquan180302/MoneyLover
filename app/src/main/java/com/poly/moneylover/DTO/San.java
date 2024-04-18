package com.poly.moneylover.DTO;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "San", foreignKeys = {
        @ForeignKey(entity = LoaiSan.class, parentColumns = "id_loaisan", childColumns = "id_loaisan", onDelete = ForeignKey.CASCADE)})
public class San {
    @PrimaryKey(autoGenerate = true)

     int id_san;
     String tensan;
     String vitrisan;
     String giasan;
     int id_loaisan;
     String tenloai;



    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] avatar_san;

    public San() {
    }

    public San(String tensan, String vitrisan, String giasan, int id_loaisan, String tenloai, byte[] avatar_san) {
        this.tensan = tensan;
        this.vitrisan = vitrisan;
        this.giasan = giasan;
        this.id_loaisan = id_loaisan;
        this.tenloai = tenloai;
        this.avatar_san = avatar_san;
    }


    public byte[] getAvatar_san() {
        return avatar_san;
    }

    public void setAvatar_san(byte[] avatar_san) {
        this.avatar_san = avatar_san;
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

    public String getVitrisan() {
        return vitrisan;
    }

    public void setVitrisan(String vitrisan) {
        this.vitrisan = vitrisan;
    }

    public String getGiasan() {
        return giasan;
    }

    public void setGiasan(String giasan) {
        this.giasan = giasan;
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
