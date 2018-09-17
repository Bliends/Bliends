package com.bliends.pc.bliends.data

import java.text.SimpleDateFormat
import java.util.*

data class Help(val type: Int) {
    var time: String

    companion object {
        const val HELP_REQUEST = 0
        const val HELP_LOST_WAY = 1
        const val HELP_LOST_MONEY = 2
    }

    init {
        val dateFormat = SimpleDateFormat("MM월 dd일 HH시 mm분", Locale.getDefault())
        val cal: Calendar = Calendar.getInstance()
        time = dateFormat.format(cal.time)
    }
}