package com.uoa.ddcapp

import java.text.SimpleDateFormat
import java.util.*

class CurrentDate {
    companion object{
        fun currentDateTime(): String {
            val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            return simpleDate.format(Date())
        }
    }
}