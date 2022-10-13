package com.uoa.ddcapp.modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uoa.ddcapp.db.LocalUserDataInterfaceDAO
import com.uoa.ddcapp.viewmodel.LocalUserDataModel

class LocalUserDataModelFactory(private val dao: LocalUserDataInterfaceDAO):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalUserDataModel::class.java)){
            return LocalUserDataModel(dao) as T
        }
        throw IllegalAccessException("Unknown view Model Class")
    }
}