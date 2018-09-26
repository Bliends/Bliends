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
import android.util.Log
import android.widget.LinearLayout
import com.bliends.pc.bliends.adapter.ActivityLogAdapter
import com.bliends.pc.bliends.data.ActivityLog
import com.bliends.pc.bliends.data.Label
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.util.*
import kotlinx.android.synthetic.main.fragment_location.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast

class LocationFragment : Fragment(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var mapView : MapView
    private lateinit var behavior: BottomSheetBehavior<LinearLayout>

    private var mLog = ArrayList<ActivityLog>()
    private lateinit var mActivityLogAdapter : ActivityLogAdapter

    //private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNTM3NzA0NTUxfQ.fegceqw-hj0XK5iBrgBAiOoabcd1EJZUb3zwYkHOSkA"
    private var token : String? = null
    private val token_2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNTM3NzA0NTI0fQ.RSqPYZfHMIElw3DpszZu3G5_5VY9DFpEwqthnIyD85M"

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED)
                    btn_open_log.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location_bottom_sheet_down, 0, 0, 0)
                else if(newState == BottomSheetBehavior.STATE_EXPANDED)
                    btn_open_log.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location_bottom_sheet_up, 0, 0, 0)
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        btn_open_log.setOnClickListener(this)

        val list = ORMUtil(context!!).tokenORM.find(Sign())
        val sign = list[list.size - 1] as Sign
        token = sign.token
        if(token == null) toast("토큰값이 없습니다!")
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

        AddMarkerUtil.setInit(context!!, googleMap!!)

        AddMarkerUtil.followUserWad(gps.latitude, gps.longitude)

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true

        mActivityLogAdapter  = ActivityLogAdapter(behavior, mLog)
        location_log.adapter = mActivityLogAdapter
        location_log.layoutManager = LinearLayoutManager(context)
        location_log.setHasFixedSize(false)
        location_log.itemAnimator = DefaultItemAnimator()

        RetrofitUtil.postService.getActivityLogList(token!!)
                .enqueue(object : RetrofitRes<ArrayList<ActivityLog>>(context!!) {
            override fun callback(code: Int, body: ArrayList<ActivityLog>?) {
                Log.e("Activity res code", code.toString())
                if(code == 200) {
                    body!!.forEach {
                        mActivityLogAdapter.add(it)
                    }
                }
            }
        })

        RetrofitUtil.postService.getLabelList(token!!)
                .enqueue(object : RetrofitRes<ArrayList<Label>>(context!!) {
                    override fun callback(code: Int, body: ArrayList<Label>?) {
                        Log.e("Label res code", code.toString())
                        if(code == 200) {
                            body!!.forEach {
                                AddMarkerUtil.addLabel(
                                        it.latitude!!.toDouble(),
                                        it.longitude!!.toDouble(),
                                        it.name!!)
                            }
                        }
                    }
                })
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

/*        RetrofitUtil.postService.postActivityLog(token_2, 0, 36.3728748F, 127.4279327F, 8000)
                .enqueue(object : RetrofitRes<ActivityLog>(context!!){
                    override fun callback(code: Int, body: ActivityLog?) {
                        Log.e("Activity Post res code", code.toString())
                        Log.e("Activty_id", body!!.id.toString())
                    }
                })*/
/*        RetrofitUtil.postService.postLabel(token_2, "집", 36.370787F, 127.431975F, 3)
                .enqueue(object : RetrofitRes<Label>(context!!){
                    override fun callback(code: Int, body: Label?) {
                        Log.e("Activity Post res code", code.toString())
                        Log.e("Activty_id", body!!.id.toString())
                    }
                })*/

/*        RetrofitUtil.postService.postActivityLog(token_2, 1, 36.370787F, 127.431975F, 0)
                .enqueue(object : RetrofitRes<ActivityLog>(context!!){
                    override fun callback(code: Int, body: ActivityLog?) {
                        Log.e("Activity Post res code", code.toString())
                        Log.e("Activty_id", body!!.id.toString())
                    }
                })*/
