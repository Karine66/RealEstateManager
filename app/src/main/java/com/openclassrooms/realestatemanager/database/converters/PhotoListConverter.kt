package com.openclassrooms.realestatemanager.database.converters

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.PhotoList
import java.util.ArrayList

class PhotoListConverter {

    @TypeConverter
    fun toPhotoList(value: String?): PhotoList {
        if (value == null || value.isEmpty()) {
            return PhotoList()
        }

        val list: List<String> = value.split(",")
        val photoList = ArrayList<String>()
        for (item in list) {
            if (item.isNotEmpty()) {
                photoList.add(item.toString())
            }
        }
        return PhotoList(photoList)
    }

    @TypeConverter
    fun toString(photoList: PhotoList?): String {

        var string = ""

        if (photoList == null) {
            return string
        }

        photoList.photoList.forEach {
            string += "$it,"
        }
        return string
    }


}