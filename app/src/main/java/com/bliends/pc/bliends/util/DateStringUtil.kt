package com.bliends.pc.bliends.util

object DateStringUtil{

    fun replaceDate(date : String) : String{
        val replaceBuffer = StringBuffer()

        replaceBuffer.append(date.substring(0, 4) + "년")
        replaceBuffer.append(" ")
        replaceBuffer.append(date.substring(5, 7) + "월")
        replaceBuffer.append(" ")
        replaceBuffer.append(date.substring(8, 10) + "일")
        replaceBuffer.append(" ")
        replaceBuffer.append(date.substring(11, 13) + "시")
        replaceBuffer.append(" ")
        replaceBuffer.append(date.substring(14, 16) + "분")
        replaceBuffer.append(" ")
        replaceBuffer.append(date.substring(17, 19) + "초")

        return replaceBuffer.toString()
    }
}