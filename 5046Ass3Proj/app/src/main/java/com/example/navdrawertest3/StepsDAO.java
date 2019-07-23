package com.example.navdrawertest3;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDAO {
    @Query("SELECT * FROM stepsEntity") List<StepsEntity> getAll();
    @Query("SELECT * FROM stepsentity WHERE userName = :userName  LIMIT 1") StepsEntity findByUserName(String userName);
    @Query("SELECT * FROM stepsentity WHERE rid = :rid LIMIT 1") StepsEntity findByID(int rid);
    @Insert
    void insertAll(StepsEntity... stepsEntities); @Insert long insert(StepsEntity stepsEntity);
    @Delete
    void delete(StepsEntity stepsEntity);
    @Update(onConflict = REPLACE) public void updateUsers(StepsEntity... stepsEntities);
    @Query("DELETE FROM stepsEntity") void deleteAll();
}
