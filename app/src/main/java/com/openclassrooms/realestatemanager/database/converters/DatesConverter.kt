package com.openclassrooms.realestatemanager.database.converters

import androidx.room.TypeConverter
import java.util.*

class DatesConverter {


        @TypeConverter
        fun fromTimestamp(value: Long?):
                Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date?):
            Long? = date?.time

}

