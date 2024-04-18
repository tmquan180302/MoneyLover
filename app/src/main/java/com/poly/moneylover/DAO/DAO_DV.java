package com.poly.moneylover.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thinhnh.fpoly.myapp.csdl.DTO.DichVu;

@Dao
public interface DAO_DV {

    @Insert
    void insertDV(DichVu dv);
    @Update
    void updataDV(DichVu dv);
    @Delete
    void deleteDV(DichVu dv);
    @Query("select * from DichVu")
    List<DichVu> getAllDV();


    @Query("select * from DichVu where id_dv= :id")
    List<DichVu> getLstheoDV(int id);
}
