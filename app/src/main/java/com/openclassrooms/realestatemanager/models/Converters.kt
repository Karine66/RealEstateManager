package com.openclassrooms.realestatemanager.models

import androidx.room.TypeConverter
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

        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time?.toLong()
        }
    }
