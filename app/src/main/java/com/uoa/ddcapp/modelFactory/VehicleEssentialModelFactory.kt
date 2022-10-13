package com.uoa.ddcapp.modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uoa.ddcapp.db.VehicleEssentialsDAO


class VehicleEssentialModelFactory(private val dao: VehicleEssentialsDAO):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(VehicleEssentialModel: Class<T>): T {
        return super.create(VehicleEssentialModel)
    }
}