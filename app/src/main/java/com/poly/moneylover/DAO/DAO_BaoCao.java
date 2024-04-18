package com.poly.moneylover.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thinhnh.fpoly.myapp.csdl.DTO.BaoCao;

@Dao
public interface DAO_BaoCao {

    @Insert
    void insertBC(BaoCao dv);
    @Update
    void updataBC(BaoCao dv);
    @Delete
    void deleteBc(BaoCao dv);
    @Query("select * from BaoCao")
    List<BaoCao> getAllBc();



}
