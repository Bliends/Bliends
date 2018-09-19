package com.bliends.pc.bliends.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.google.android.gms.maps.*
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.bliends.pc.bliends.adapter.LocationAdapter
import com.bliends.pc.bliends.data.Location
import com.bliends.pc.bliends.util.AddMarkerUtil
import com.bliends.pc.bliends.util.GPSUtil
import kotlinx.android.synthetic.main.fragment_location.*
import org.jetbrains.anko.find

class LocationFragment : Fragment(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var mapView : MapView

    private lateinit var behavior: BottomSheetBehavior<LinearLayout>

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

        val gps = GPSUtil(context!!)
        gps.getLocation()
        gps.stopUsingGPS()
        AddMarkerUtil.addWad(context!!, googleMap!!, 36.3907123,127.3632759, R.drawable.location_wad)
        AddMarkerUtil.followUserWad(context!!, googleMap, gps.latitude, gps.longitude)
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