package com.uoa.ddcapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uoa.ddcapp.model.LocalUserData
import com.uoa.ddcapp.model.Trip
import com.uoa.ddcapp.model.VehicleEssentials

@Database(entities = [LocalUserData::class, VehicleEssentials::class,Trip::class], version=1, exportSchema = false)
abstract class PhDSafeDriveDB:RoomDatabase() {
    abstract fun localUserDataInterfaceDAO(): LocalUserDataInterfaceDAO
    abstract fun vehicleEssentialsDAO():VehicleEssentialsDAO
    abstract fun tripDAO():TripDAO

    companion object{
        @Volatile
        private  var INSTANCE: PhDSafeDriveDB?=null
        // A function to get the an instance of the PhDSafeDriveDB object
// if it exists, in the application context. or create One database
// instance if none exists
        fun getInstance(context: Context): PhDSafeDriveDB{
            synchronized(this){
                var instance= INSTANCE
                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        PhDSafeDriveDB::class.java,
                        "phd_database"
                    ).build()
                }
                return instance
            }
        }
    }
}