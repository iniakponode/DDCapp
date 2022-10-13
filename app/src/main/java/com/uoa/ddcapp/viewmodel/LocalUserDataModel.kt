package com.uoa.ddcapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uoa.ddcapp.db.LocalUserDataInterfaceDAO
import com.uoa.ddcapp.model.LocalUserData

import kotlinx.coroutines.launch

class LocalUserDataModel(private val dao: LocalUserDataInterfaceDAO):ViewModel() {
    val localUserData=dao.getAllLocalUserData()

    fun insertlocaluserdata(localUserData: LocalUserData){
        viewModelScope.launch {
            dao.insertLocalUserData(localUserData)
        }
    }

    fun updatelocaluserdata(localUserData: LocalUserData){
        viewModelScope.launch {
            dao.updateLocalUserData(localUserData)
        }
    }

    fun deletelocaluserdata(localUserData: LocalUserData){
        viewModelScope.launch {
            dao.deleteLocalUserData(localUserData)
        }
    }
}