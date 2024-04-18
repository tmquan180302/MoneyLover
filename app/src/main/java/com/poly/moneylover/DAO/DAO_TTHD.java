package com.poly.moneylover.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thinhnh.fpoly.myapp.csdl.DTO.TrangThaiHoaDon;

@Dao
public interface DAO_TTHD {


    @Insert
    void insertTTHD(TrangThaiHoaDon trangThaiHoaDon);
    @Update
    void updataTTHD(TrangThaiHoaDon trangThaiHoaDon);
    @Delete
    void deleteTTHD(TrangThaiHoaDon trangThaiHoaDon);
    @Query("select * from TrangThaiHoaDon")
    List<TrangThaiHoaDon> getAllTTHD();
}
