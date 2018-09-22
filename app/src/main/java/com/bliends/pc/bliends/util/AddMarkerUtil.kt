package com.bliends.pc.bliends.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bliends.pc.bliends.R
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import org.jetbrains.anko.find
import org.jetbrains.anko.windowManager

@SuppressLint("StaticFieldLeak")
object AddMarkerUtil{

    var mainViewPager : ViewPager? = null
    var context : Context? = null
    private var googleMap : GoogleMap? = null

    private var geoCoder : Geocoder? = null
    private lateinit var cameraUpdate : CameraUpdate
    private var lastMarker : Marker? = null
    private var userBindMarker : Marker? = null

    private var markerRootView : View? = null
    private var logMarkerMessage : TextView? = null

    fun setInit(context: Context, googleMap: GoogleMap){
        this.context = context
        this.googleMap = googleMap
        initMarker(context)
    }

    @SuppressLint("InflateParams")
    private fun initMarker(context: Context){
        markerRootView = LayoutInflater.from(context).inflate(R.layout.log_marker, null)
        logMarkerMessage = markerRootView!!.find(R.id.log_marker_message)
    }

    fun followUserWad(lat: Double, lng : Double){
        val latLng = LatLng(lat, lng)

        if(userBindMarker != null) userBindMarker!!.remove()

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.25F)
        googleMap!!.animateCamera(cameraUpdate)

        geoCoder = Geocoder(context)

        val locationName : Address? = geoCoder !!.getFromLocation(lat, lng, 1)[0]
        val marker = MarkerOptions()
                .position(latLng)
                .title(locationName!!.featureName)
                .snippet(locationName.getAddressLine(0))

        marker.icon(bitmapDescriptorFromVector(context, R.drawable.location_wad))

        userBindMarker = googleMap!!.addMarker(marker)
    }

    @SuppressLint("SetTextI18n")
    fun addWad(lat: Double, lng : Double, message: String){
        if(lastMarker != null) lastMarker!!.remove()

        geoCoder = Geocoder(context)

        val latLng = LatLng(lat, lng)

        val locationName : Address? = geoCoder !!.getFromLocation(lat, lng, 1)[0]
        val marker = MarkerOptions().position(latLng)

        logMarkerMessage!!.text = message
        marker.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context!!, markerRootView!!)))

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.25F)
        googleMap!!.animateCamera(cameraUpdate)

        lastMarker = googleMap!!.addMarker(marker)
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context!!, vectorResId)
        vectorDrawable!!.setBounds(0, 0, 150, 150)
        val bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun createDrawableFromView(context: Context, view: View) : Bitmap {
        val displayMetrics = DisplayMetrics()

        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()

        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888);
        val canvas = Canvas(bitmap)

        view.draw(canvas)
        return bitmap
    }
}