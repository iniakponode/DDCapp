package com.uoa.ddcapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uoa.ddcapp.db.VehicleEssentialsDAO
import com.uoa.ddcapp.model.VehicleEssentials
import kotlinx.coroutines.launch

class VehicleEssentialsModel(private val dao: VehicleEssentialsDAO):ViewModel() {
    val vehicleEssentials=dao.getAllVehicleEssentialData()

    fun insertvehicleessentials(vehicleEssentials: VehicleEssentials){
        viewModelScope.launch {
            dao.insertVehicleEssentialData(vehicleEssentials)
        }
    }

    fun updatevehicleessentials(vehicleEssentials: VehicleEssentials){
        viewModelScope.launch {
            dao.updateVehicleEssentialData(vehicleEssentials)
        }
    }

    fun deletevehicleessentials(vehicleEssentials: VehicleEssentials){
        viewModelScope.launch {
            dao.deleteVehicleEssentialData(vehicleEssentials)
        }
    }
}