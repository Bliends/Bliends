package com.bliends.pc.bliends.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ActivityLog{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Float? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Float? = null

    @SerializedName("payments")
    @Expose
    var payments: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("group_id")
    @Expose
    var groupId: Int? = null

    @SerializedName("label_id")
    @Expose
    var labelId: Int? = null

    @SerializedName("label")
    @Expose
    var label: Label? = null
}