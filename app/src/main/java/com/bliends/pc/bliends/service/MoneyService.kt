package com.bliends.pc.bliends.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import com.bliends.pc.bliends.util.TTSUtil
import org.jetbrains.anko.toast
import java.io.IOException
import java.lang.Exception

class MoneyService : Service() {
    var bt: BluetoothSPP = BluetoothSPP(this)
    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        bluetoothStart()
        Log.d("test", "서비스의 onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand")
        bluetoothStart()
        return super.onStartCommand(intent, flags, startId)
    }

    fun bluetoothStart() {
        try {
            if (!bt.isBluetoothEnabled) {
                bt.enable()
                toast("블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
                TTSUtil.usingTTS(this@MoneyService, "블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
                Log.e("isServiceAvailable","노연결")
            } else {
                if (!bt.isServiceAvailable) {
                    bt.setupService()
                    bt.startService(BluetoothState.DEVICE_OTHER)
                    bt.autoConnect("BLIENDS")
                    Log.e("isServiceAvailable","연결")
                }
            }

            bt.setupService()
            bt.startService(BluetoothState.DEVICE_OTHER)
            bt.autoConnect("BLIENDS")

            bt.setAutoConnectionListener(object : BluetoothSPP.AutoConnectionListener {
                override fun onNewConnection(name: String, address: String) {
                    //새로운 연결일때
                    Log.e("new", "succes")
                }

                override fun onAutoConnectionStarted() {
                    //자동 연결
                    Log.e("auto", "succes")
                    toast("기기와 정상적으로 연결되었습니다.")
                }
            })


            bt!!.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
                override fun onDeviceDisconnected() {
                }

                override fun onDeviceConnected(name: String?, address: String?) {
                    toast("기기와 연걸이 정상적으로 완료되었습니다.")
                }

                override fun onDeviceConnectionFailed() {
                    toast("기기와 연걸이 실패하였습니다.")
                }
            })

            bt.setOnDataReceivedListener { data, message ->
                toast(message!!)
                if (message == "1t") {
                    TTSUtil.usingTTS(this@MoneyService, "1000원이 인식되었습니다.")
                } else if (message == "5t") {
                    TTSUtil.usingTTS(this@MoneyService, "5000원이 인식되었습니다.")
                } else if (message == "1m") {

                    TTSUtil.usingTTS(this@MoneyService, "10000원이 인식되었습니다.")
                } else if (message == "5m") {
                    TTSUtil.usingTTS(this@MoneyService, "50000원이 인식되었습니다.")
                } else {
                    TTSUtil.usingTTS(this@MoneyService, message)
                }
            }
        } catch (e: Exception) {
            toast("블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
            TTSUtil.usingTTS(this@MoneyService, "블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
        }
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        if (!bt.isBluetoothEnabled) {
            bt.enable()
            Log.e("asdfasdfasdfsfdasdfa","나재민")
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                bt.autoConnect("BLIENDS")

                Log.e("asdfasdfasdfsfdasdfa","나재딩")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 서비스가 종료될 때 실행
        bt.stopService()
        Log.d("test", "서비스의 onDestroy")
    }

}