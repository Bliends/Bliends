package com.bliends.pc.bliends.fragment

import android.annotation.TargetApi
import android.app.Dialog
import android.app.DialogFragment
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.*
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.dialog_add_label.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.bliends.pc.bliends.activity.TutroialShow
import com.bliends.pc.bliends.data.Label
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.util.AddMarkerUtil.createDrawableFromView
import com.bliends.pc.bliends.util.GPSUtil
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.RetrofitRes
import com.bliends.pc.bliends.util.RetrofitUtil
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AddLabelDialogFragment : DialogFragment(), OnMapReadyCallback, View.OnClickListener{

    private var mDialog : Dialog? = null
    private lateinit var mapView : MapView
    private lateinit var geocoder: Geocoder
    private var googleMap : GoogleMap? = null
    private lateinit var cameraUpdate : CameraUpdate

    private var address : Address? = null
    private var lastMarker : Marker? = null

    private var markerRootView : View? = null
    private var logMarkerMessage : TextView? = null

//    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNTM3NzA0NTUxfQ.fegceqw-hj0XK5iBrgBAiOoabcd1EJZUb3zwYkHOSkA"
    private var token : String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = Dialog(activity, R.style.DialogFragment)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mDialog!!.window.attributes)

        val view = View.inflate(activity, R.layout.dialog_add_label, null)
        mDialog!!.window.attributes = layoutParams
        mDialog!!.setContentView(view)

        return mDialog as Dialog
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cancel.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        if(isStart) {
            set_label_name.setText("집")
            set_label_name.isEnabled = false
        }
        label_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(v.textSize > 0) {
                    val addressList = geocoder.getFromLocationName(v.text.toString(), 10)

                    try{
                        address = addressList[0]
                        showMarker(address!!.latitude, address!!.longitude, true)
                    }catch (e : Exception){
                        toast("좀 더 정확한 주소를 입력해주세요.")
                    }
                }
                return@OnEditorActionListener true
            }
            false
        })
        val list = ORMUtil(context).tokenORM.find(Sign())
        val sign = list[list.size - 1] as Sign
        token = sign.token
        if(token == null) toast("토큰값이 없습니다!")
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun showMarker(lat : Double, lng : Double, fuck : Boolean){
        if (lastMarker != null) lastMarker!!.remove()

        val latLng = LatLng(lat, lng)

        val marker = MarkerOptions().position(latLng)

        if(fuck) logMarkerMessage!!.text = address!!.getAddressLine(0)
        else logMarkerMessage!!.text = "현재 위치"
        marker.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context!!, markerRootView!!)))

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.25F)
        googleMap!!.animateCamera(cameraUpdate)

        lastMarker = googleMap!!.addMarker(marker)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val rootView = inflater.inflate(R.layout.dialog_add_label, container, false)

        mapView = rootView.find(R.id.label_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        markerRootView = LayoutInflater.from(context).inflate(R.layout.log_marker, null)
        logMarkerMessage = markerRootView!!.find(R.id.log_marker_message)

        mDialog!!.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
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

    @TargetApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(activity)
        this.googleMap = googleMap
        geocoder = Geocoder(context)

        val gps = GPSUtil(context)
        gps.getLocation()
        gps.stopUsingGPS()

        showMarker(gps.latitude, gps.longitude, false)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_add ->{
                if((address != null && lastMarker != null) && set_label_name.textSize > 0){
                    RetrofitUtil.postService.postLabel(
                            token!!,
                            set_label_name.text.toString(),
                            address!!.latitude.toFloat(),
                            address!!.longitude.toFloat(),
                            1) .enqueue(@TargetApi(Build.VERSION_CODES.M)
                    object : RetrofitRes<Label>(context){
                        override fun callback(code: Int, body: Label?) {
                            if(code == 201){
                                toast("라벨을 추가했습니다.")
                                startActivity<TutroialShow>("User" to "Protector")
                                dismiss()
                            }else{
                                toast("라벨를 추가하는데 실패했습니다.")
                            }
                        }
                    })
                }else{
                    toast("정보를 입력해주세요!")
                }
            }
            R.id.btn_cancel -> dismiss()
        }
    }

    companion object {
        private var isStart = false
        fun newInstance(isStart: Boolean): AddLabelDialogFragment {
            this.isStart = isStart
            return AddLabelDialogFragment()
        }
    }
}