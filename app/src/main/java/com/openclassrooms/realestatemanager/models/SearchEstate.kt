package com.openclassrooms.realestatemanager.models

import androidx.room.Entity

@Entity
data class SearchEstate(var estateType: String?,

        var city: String?,
        var minRooms: Int?,
        var maxRooms: Int?,
        var minSurface:Int?,
        var maxSurface:Int?,
        var minPrice:Double?,
        var maxPrice:Double?,
        var minUpOfSaleDate:String?,
        var maxOfSaleDate:String?,
        var minPhotos:UriList,
        var maxPhotos:UriList,
        var schools:Boolean,
        var stores:Boolean,
        var park:Boolean,
        var restaurants:Boolean,
        var sold:Boolean


        ) {
    constructor() : this("", "",null,null,null,null,null, null,"", "",
    UriList(), UriList(), false,false,false,false,false)
}

