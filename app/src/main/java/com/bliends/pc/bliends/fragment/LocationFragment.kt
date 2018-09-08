package com.bliends.pc.bliends.fragment

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor

class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView : MapView
    private lateinit var cameraUpdate : CameraUpdate

    private var googleMap : GoogleMap? = null
    private var geocoder : Geocoder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val rootView = inflater.inflate(R.layout.fragment_location, container, false)
        mapView = rootView.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        geocoder = Geocoder(context)
        return rootView
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(activity)

        this.googleMap = googleMap!!

        setLocation(36.39145, 127.363096)
    }

    private fun setLocation(lat : Double, lng : Double){
        val latLng = LatLng(lat, lng)

        val locationName : Address? = geocoder!!.getFromLocation(lat, lng, 1)[0]
        val marker = MarkerOptions()
                .position(latLng)
                .title(locationName!!.featureName)
                .snippet(locationName.getAddressLine(0))

        marker.icon(bitmapDescriptorFromVector(context, R.drawable.location_wad))

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.25F)

        googleMap!!.animateCamera(cameraUpdate)

        googleMap!!.addMarker(marker)
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
