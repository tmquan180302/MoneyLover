package com.poly.moneylover.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thinhnh.fpoly.myapp.csdl.DTO.LoaiSan;
@Dao
public interface DAO_LOAISAN {

    @Insert
    void insertLoaiSan(LoaiSan cs);
    @Update
    void updataLoaiSan(LoaiSan cs);
    @Delete
    void deleteLoaiSan(LoaiSan cs);
    @Query("select * from LoaiSan")
    List<LoaiSan> getAllLoaiSan();


    @Query("select * from LoaiSan where id_loaisan= :id")
    List<LoaiSan> getLstheoID(int id);
}
