package com.openclassrooms.realestatemanager.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.models.PhotoDescription
import java.lang.reflect.Type


class PhotoDescriptionConverter {
//2ème solution
//    fun toListOfStrings(list: String): List<String> {
//        return list.split(",")
//    }
//    @TypeConverter
//    fun fromListOfStrings(list: List<String>): String {
//        return list.joinToString(",")
//    }
//    1er converter
//    @TypeConverter
//    fun toPhotoDescription(flatStringList: String): List<String> {
//        return flatStringList.split(",")
//    }
    @TypeConverter
    fun fromPhotoDescription(photoDescription: List<String>): String {
        return photoDescription.joinToString(",")
    }

    @TypeConverter
    fun toPhotoDescription(value: String?): PhotoDescription {
        if (value == null || value.isEmpty()) {
            return PhotoDescription()
        }

        val list: List<String> = value.split(",")
        val photoDescription = ArrayList<String>()
        for (item in list) {
            if (item.isNotEmpty()) {
                photoDescription.add(item.toString())
            }
        }
        return PhotoDescription(photoDescription)
    }

    @TypeConverter
    fun toString(photoDescription: PhotoDescription?): String {

        var string = ""

        if (photoDescription == null) {
            return string
        }

        photoDescription.photoDescription.forEach {
            string += "$it,"
        }
        return string
    }

}