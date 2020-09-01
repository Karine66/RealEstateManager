package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate

@Dao
interface EstateDAO {


        @Query("SELECT * FROM Estate")
        fun getEstates(): LiveData<List<Estate>>

        @Query("SELECT * FROM Estate WHERE mandateNumberID = :mandateNumberID")
        fun getEstate(mandateNumberID: Long): LiveData<Estate>

        @Insert
        fun insertEstate(estate: Estate):Long
        @Update
        fun updateEstate(estate: Estate):Int
        @Query("DELETE FROM Estate WHERE mandateNumberID = :mandateNumberID")
        fun deleteItem(mandateNumberID:Long):Int

//        @Insert
//        fun insertPhotoList (photoList: PhotoList) : String
//
//        @Query("SELECT * FROM Estate")
//        fun getPhotoList() : LiveData<List<PhotoList>>

//       @Update
//       fun updatePhotoList(photoList: PhotoList) : String

//        @Query("DELETE FROM Estate WHERE photoList")
//                fun deletePhotoList():String
    }
