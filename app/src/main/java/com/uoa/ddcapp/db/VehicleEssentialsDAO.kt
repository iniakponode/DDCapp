package com.uoa.ddcapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uoa.ddcapp.model.VehicleEssentials


@Dao
interface VehicleEssentialsDAO {
    @Insert
    suspend fun insertVehicleEssentialData(vehicleEssentials: VehicleEssentials)

    @Update
    suspend fun updateVehicleEssentialData(vehicleEssentials: VehicleEssentials)

    @Delete
    suspend fun deleteVehicleEssentialData(vehicleEssentials: VehicleEssentials)

    @Query("Select * FROM vehicle_essentials")
    fun getAllVehicleEssentialData(): LiveData<List<VehicleEssentials>>
}