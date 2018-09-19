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
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.bliends.pc.bliends.adapter.LocationAdapter
import com.bliends.pc.bliends.data.Location
import com.bliends.pc.bliends.util.GPSUtil
import com.google.android.gms.maps.model.BitmapDescriptor
import kotlinx.android.synthetic.main.fragment_location.*
import org.jetbrains.anko.find

class LocationFragment : Fragment(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var mapView : MapView
    private lateinit var cameraUpdate : CameraUpdate

    private lateinit var behavior: BottomSheetBehavior<LinearLayout>

    private var googleMap : GoogleMap? = null
    private var geoCoder : Geocoder? = null

    private var mLog = ArrayList<Location>()
    private lateinit var mLocationAdapter : LocationAdapter

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = BottomSheetBehavior.from(bottom_sheet)
        btn_open_log.setOnClickListener(this)

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true

        mLocationAdapter  = LocationAdapter(mLog)
        location_log.adapter = mLocationAdapter
        location_log.layoutManager = mLayoutManager
        location_log.setHasFixedSize(false)
        location_log.itemAnimator = DefaultItemAnimator()

        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
        mLocationAdapter .add(Location())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val rootView = inflater.inflate(R.layout.fragment_location, container, false)
        mapView = rootView.find(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        geoCoder = Geocoder(context)

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

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(activity)
        this.googleMap = googleMap!!

        val gps = GPSUtil(context!!)
        gps.getLocation()
        setLocation(gps.latitude, gps.longitude)
    }

    private fun setLocation(lat : Double, lng : Double){
        val latLng = LatLng(lat, lng)

        val locationName : Address? = geoCoder !!.getFromLocation(lat, lng, 1)[0]
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

    override fun onClick(v: View?) {
        if(behavior.state == BottomSheetBehavior.STATE_COLLAPSED){
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        else if(behavior.state == BottomSheetBehavior.STATE_EXPANDED){
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}