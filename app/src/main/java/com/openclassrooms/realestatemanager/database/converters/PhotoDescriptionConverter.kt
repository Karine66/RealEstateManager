package com.openclassrooms.realestatemanager.database.converters

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.PhotoDescription
import com.openclassrooms.realestatemanager.models.PhotoList
import java.util.ArrayList

class PhotoDescriptionConverter {

//    @TypeConverter
//    fun toPhotoDescription(flatStringList: String): List<String> {
//        return flatStringList.split(",")
//    }
//    @TypeConverter
//    fun fromListOfStrings(listOfString: List<String>): String {
//        return listOfString.joinToString(",")
//    }

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