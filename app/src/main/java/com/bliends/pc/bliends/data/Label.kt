package com.bliends.pc.bliends.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Label{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Float? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Float? = null

    @SerializedName("importance")
    @Expose
    var importance: Int? = null

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