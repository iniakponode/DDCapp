package com.uoa.ddcapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uoa.ddcapp.model.LocalUserData


@Dao
interface LocalUserDataInterfaceDAO {
        @Insert
        suspend fun insertLocalUserData(localUserData: LocalUserData)
        @Update
        suspend fun updateLocalUserData(localUserData: LocalUserData)
        @Delete
        suspend fun deleteLocalUserData(localUserData: LocalUserData)

        @Query("Select * FROM local_user_data")
        fun getAllLocalUserData(): LiveData<List<LocalUserData>>

        @Query("Select * FROM local_user_data WHERE id=:id")
        @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
        @RewriteQueriesToDropUnusedColumns
        fun getT(id:Int):List<LocalUserData>
}