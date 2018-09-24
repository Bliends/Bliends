package com.bliends.pc.bliends.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashBoardDate{

    @SerializedName("month")
    @Expose
    var month: Int? = null

    @SerializedName("date")
    @Expose
    var date: Int? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null
}