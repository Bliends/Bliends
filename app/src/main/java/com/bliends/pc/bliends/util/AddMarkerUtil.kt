package com.bliends.pc.bliends.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

object AddMarkerUtil{

    private var geoCoder : Geocoder? = null
    private lateinit var cameraUpdate : CameraUpdate
    private var lastMarker : Marker? = null
    private var userBindMarker : Marker? = null

    fun addWad(context: Context, googleMap: GoogleMap, lat: Double, lng : Double, vectorResId: Int){
        if(lastMarker != null) lastMarker!!.remove()

        geoCoder = Geocoder(context)

        val latLng = LatLng(lat, lng)

        val locationName : Address? = geoCoder !!.getFromLocation(lat, lng, 1)[0]
        val marker = MarkerOptions()
                .position(latLng)
                .title(locationName!!.featureName)
                .snippet(locationName.getAddressLine(0))

        marker.icon(bitmapDescriptorFromVector(context, vectorResId))

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.25F)
        googleMap.animateCamera(cameraUpdate)

        lastMarker = googleMap.addMarker(marker)
    }

    fun followUserWad(context: Context, googleMap: GoogleMap, lat: Double, lng : Double){
        if(userBindMarker != null) userBindMarker!!.remove()

        geoCoder = Geocoder(context)

        val latLng = LatLng(lat, lng)

        val locationName : Address? = geoCoder !!.getFromLocation(lat, lng, 1)[0]
        val marker = MarkerOptions()
                .position(latLng)
                .title(locationName!!.featureName)
                .snippet(locationName.getAddressLine(0))

        marker.icon(bitmapDescriptorFromVector(context, R.drawable.location_wad))

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.25F)
        googleMap.animateCamera(cameraUpdate)

        userBindMarker = googleMap.addMarker(marker)
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context!!, vectorResId)
        vectorDrawable!!.setBounds(0, 0, 150, 150)
        val bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}