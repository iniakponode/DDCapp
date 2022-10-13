package com.uoa.ddcapp

import android.content.SharedPreferences
import android.util.Log

class StoredToken {

    companion object{

        fun getStoredDeviceToken(sf:SharedPreferences,tokenFileID:String): String {
            val deviceToken = sf.getString(tokenFileID, null)
            val devT = deviceToken.toString()
            Log.i("Device-TokenSaved", deviceToken.toString())
            return devT
        }
        fun validateToken(token:String):Boolean{
            return !(token==null)
        }


        }
    }