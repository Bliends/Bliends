package com.bliends.pc.bliends.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Group{

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("patient_id")
    @Expose
    var patientId: Int? = null

    @SerializedName("caregiver_id")
    @Expose
    var caregiverId: Int? = null

    @SerializedName("patient")
    @Expose
    var patient: Patient? = null

    @SerializedName("caregiver")
    @Expose
    var caregiver: Caregiver? = null

    inner class Caregiver {

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("userid")
        @Expose
        var userid: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }

    inner class Patient {

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("userid")
        @Expose
        var userid: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null
    }
}