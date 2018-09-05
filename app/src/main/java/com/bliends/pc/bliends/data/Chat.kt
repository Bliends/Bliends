package com.bliends.pc.bliends.data

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


data class Chat(val type : Int){
    lateinit var message: String

    constructor(message: String?, type: Int) : this(type){
        this.message = message!!
    }

    companion object {
        const val TYPE_OTHER_CHAT = 1
        const val TYPE_MY_CHAT = 0
        const val TYPE_TIME_CHAT = 2
    }
    init {
        if(type == TYPE_TIME_CHAT){
            val dateFormat : DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val cal : Calendar = Calendar.getInstance()
            message = dateFormat.format(cal.time)
        }
    }
}