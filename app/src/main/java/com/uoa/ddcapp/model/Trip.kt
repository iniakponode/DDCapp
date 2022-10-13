package com.uoa.ddcapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trip")
data class Trip(
    @SerializedName("userId")
    val userId: String,

    @PrimaryKey(autoGenerate = false)
    @SerializedName("tripId")
    @ColumnInfo(name = "tripId")
    val tripId: String,

    @SerializedName("videoURI")
    val videoURI: String,

    @SerializedName("distractionStatus")
    val distractionStatus: String,

    @SerializedName("drunk_state")
    val drunk_state: Int,

    @SerializedName("no_OfDrinks")
    val noOfDrinks:Int,

    @SerializedName("dateTime")
    val dateTime: String

)
