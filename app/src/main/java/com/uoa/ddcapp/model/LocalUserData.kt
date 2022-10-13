package com.uoa.ddcapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName="local_user_data")
data class LocalUserData(
    @SerializedName("companyId")
    val companyId: String,
    @SerializedName("groupId")
    val groupId:String,

    @SerializedName("dateTime")
    val dateTime: String,

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("userId")
    val userId: String
)