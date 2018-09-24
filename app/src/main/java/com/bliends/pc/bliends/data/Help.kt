package com.bliends.pc.bliends.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Help{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Float? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Float? = null

    @SerializedName("situation")
    @Expose
    var situation: String? = null

    @SerializedName("filename")
    @Expose
    var filename: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("group_id")
    @Expose
    var groupId: Int? = null
}