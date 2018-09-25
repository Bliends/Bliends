package com.bliends.pc.bliends.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashBoardLabel{

    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("label_id")
    @Expose
    var labelId: Int? = null

    @SerializedName("label")
    @Expose
    var label: Label? = null
}