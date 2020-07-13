package com.openclassrooms.realestatemanager.models

import androidx.room.TypeConverter
import java.lang.reflect.Type
import java.util.*


class Converters {
    //for list photo
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