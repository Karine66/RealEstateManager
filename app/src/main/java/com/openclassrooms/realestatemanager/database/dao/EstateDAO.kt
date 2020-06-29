package com.openclassrooms.realestatemanager.database.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate

@Dao
interface EstateDAO {

    @Query ("SELECT * FROM estate ")
    fun getEstate() : List<Estate>

    @Query ("SELECT * FROM estate where mandateNumberID = :mandateNumberID")
    fun getMandateNumberId(mandateNumberID:Long) : Estate
    
    @Insert
    fun insertEstate(vararg listCategories : Estate)

    @Update
    fun updateEstate(task:Estate)

    @Delete
    fun deleteEstate(task: Estate)
}