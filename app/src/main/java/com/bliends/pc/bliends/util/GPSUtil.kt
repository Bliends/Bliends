package com.bliends.pc.bliends.util

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.util.Log

class GPSUtil(private val mContext: Context) : Service(), LocationListener {

    var isGPSEnabled = false
    var isNetworkEnabled = false

    private var location: Location? = null
    private var lat: Double = 0.toDouble()
    private var lon: Double = 0.toDouble()

    private var locationManager: LocationManager? = null

    val latitude: Double
        get() {
            if (location != null) {
                lat = location!!.latitude
            }
            return lat
        }

    val longitude: Double
        get() {
            if (location != null) {
                lon = location!!.longitude
            }
            return lon
        }

    @TargetApi(23)
    fun getLocation(): Location? {
        if ((Build.VERSION.SDK_INT >= 23 &&
                        (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))) {
            return null
        }

        try {
            locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            assert(locationManager != null)

            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.e("Not Enabled!!1", "ASdfdsfsd")
                return null
            } else {
                locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)

                if (locationManager != null) {
                    location = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    Log.e("location NETWORK", (location == null).toString())

                    if (location != null) {
                        lat = location!!.latitude
                        lon = location!!.altitude
                    }

                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager!!.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                            if (locationManager != null) {
                                location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                Log.e("location GPS_PROVIDER", (location == null).toString())

                                if (location != null) {
                                    lat = location!!.latitude
                                    lon = location!!.altitude
                                }
                            }
                        }
                    }
                } else {
                    Log.e("locationManager", "is NULLL!!!!!!!!!!!!!")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location
    }

    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSUtil)
        }
    }

    fun showSettingsAlert() {
        makeDialog()
    }

     fun makeDialog() {
        val alt_bld = AlertDialog.Builder(mContext)

        alt_bld.setMessage("GPS 사용이 필요합니다.\n설정창으로 가시겠습니까?").setCancelable(
                false).setPositiveButton("OK"
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
        }.setNegativeButton("NO"
        ) { dialog, which -> dialog.cancel() }
        val alertDialog = alt_bld.create()
        alertDialog.show()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

    companion object {

        // 최소 GPS 정보 업데이트 거리 10미터
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10

        // 최소 GPS 정보 업데이트 시간 밀리세컨(1분)
        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    }
}