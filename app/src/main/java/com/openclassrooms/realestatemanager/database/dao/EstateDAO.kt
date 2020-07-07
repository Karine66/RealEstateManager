package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate

@Dao
interface EstateDAO {


        @Query("SELECT * FROM Estate WHERE mandateNumberID = :mandateNumberID")
        fun getEstate(mandateNumberID:Long): LiveData<List<Estate>>

        @Query("SELECT * FROM Estate")
        fun getEstate(): LiveData<Estate>

        @Insert
        fun insertEstate(estate: Estate):Long
        @Update
        fun updateEstate(estate: Estate):Int
        @Query("DELETE FROM Estate WHERE mandateNumberID = :mandateNumberID")
        fun deleteItem(mandateNumberID:Long):Int
    }
