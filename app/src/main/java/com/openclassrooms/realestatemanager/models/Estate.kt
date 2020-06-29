package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Estate(@PrimaryKey (autoGenerate = true) val MandateNumberID:Long)
var estateType:String = ""
var surface: Int? = null
var rooms:Int?=null
var bedrooms:Int?=null
var bathrooms:Int?=null
var ground:Int?=null
var price: Double? =null
var description:String =""
var address:String=""
var postalCode:Int?=null
var city:String=""
var schools:Boolean = false
var stores:Boolean=false
var park:Boolean=false
var restaurants:Boolean=false
var avalaible:Boolean=false
var sold:Boolean=false
var upOfSaleDate:Date?=null
var soldDate:Date?=null
var agentName:String=""
//var photo:ArrayList<String>
//var video:








