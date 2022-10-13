package com.uoa.ddcapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "vehicle_essentials")
data class VehicleEssentials(

    @SerializedName("totalMile")
    val totalMile: String,

    @SerializedName("totalPassenger")
    val totalPassengers: String,

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,


    @SerializedName("userId")
    val userId: String
)