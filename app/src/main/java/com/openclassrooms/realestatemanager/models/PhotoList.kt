package com.openclassrooms.realestatemanager.models

import java.io.Serializable


data class PhotoList(
        val photoList: java.util.ArrayList<String> = ArrayList(),
        var photoDescription : java.util.ArrayList<String> = ArrayList()
) : Serializable

