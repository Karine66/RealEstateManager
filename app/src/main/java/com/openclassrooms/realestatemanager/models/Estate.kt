package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.*

@Entity
data class Estate(@PrimaryKey (autoGenerate = true) val mandateNumberID: Long?,

                  var estateType:String?,
                  var surface: Int?,
                  var rooms:Int?,
                  var bedrooms:Int?,
                  var bathrooms:Int?,
                  var ground:Int?,
                  var price: Double?,
                  var description:String?,
                  var address:String?,
                  var postalCode:Int?,
                  var city:String?,
                  var schools:Boolean,
                  var stores:Boolean,
                  var park:Boolean,
                  var restaurants:Boolean,
                  var sold:Boolean,
                  var upOfSaleDate: String?,
                  var soldDate:String?,
                  var agentName:String
//                  var photoList: List<String>


//var video:ArrayList<String> = TODO())
)
{
    constructor() : this (null,"",null,null,null,null,null,null,"","",
    null,"",false,false,false,false,false, null,null,"")


}



//fun fromContentValues(values: ContentValues):Estate{
//    val estate = Estate()
//    if (values.containsKey("estateType")) estate.estateType =values.getAsString("estateType")
//    if (values.containsKey("surface")) estate.surface =values.getAsInteger("surface")
//    if (values.containsKey("rooms")) estate.rooms =values.getAsInteger("rooms")
//    if (values.containsKey("bedrooms")) estate.bedrooms=values.getAsInteger("bedrooms")
//    if (values.containsKey("bathrooms")) estate.bathrooms =values.getAsInteger("bathrooms")
//    if (values.containsKey("ground")) estate.ground =values.getAsInteger("ground")
//    if (values.containsKey("price")) estate.price =values.getAsDouble("price")
//    if (values.containsKey("description")) estate.description=values.getAsString("description")
//    if (values.containsKey("address")) estate.address=values.getAsString("address")
//    if (values.containsKey("postalCode")) estate.postalCode=values.getAsInteger("postalCode")
//    if (values.containsKey("city")) estate.city=values.getAsString("city")
//    if (values.containsKey("schools")) estate.schools=values.getAsBoolean("schools")
//    if (values.containsKey("stores")) estate.stores=values.getAsBoolean("stores")
//    if (values.containsKey("park")) estate.park=values.getAsBoolean("park")
//    if (values.containsKey("restaurants")) estate.restaurants=values.getAsBoolean("restaurants")
//    if (values.containsKey("upOfSaleDate")) estate.upOfSaleDate=values.get("upOfSaleDate")
//    if (values.containsKey("soldDate")) estate.soldDate=values.getAsLong("soldDate")
//    if (values.containsKey("agentName")) estate.agentName=values.getAsString("agentName")
//
//    return estate }










