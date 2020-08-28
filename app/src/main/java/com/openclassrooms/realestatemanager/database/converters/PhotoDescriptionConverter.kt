package com.openclassrooms.realestatemanager.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.models.PhotoDescription
import java.lang.reflect.Type


class PhotoDescriptionConverter {

//    @TypeConverter
//    fun fromPhotoDescription(photoDescription: List<String>): String {
//        return photoDescription.joinToString("|")
//    }

    @TypeConverter
    fun toPhotoDescription(value: String?): PhotoDescription {
        if (value == null || value.isEmpty()) {
            return PhotoDescription()
        }

        val list: List<String> = value.split(";")
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