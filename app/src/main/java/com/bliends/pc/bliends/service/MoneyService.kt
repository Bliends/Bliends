package com.bliends.pc.bliends.service

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import com.bliends.pc.bliends.util.TTSUtil
import org.jetbrains.anko.toast
import java.io.IOException
import java.lang.Exception
import android.content.IntentFilter



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
            bt.setupService()
            bt.startService(BluetoothState.DEVICE_OTHER)
            bt.autoConnect("BLIENDS")

            Log.e("isServiceAvailable", "연결")


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
    }

    val mBroadcastReceiver1 = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action = p1!!.action

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                val state = p1.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        Log.e("STATE_OFF","STATE_OFF")
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        Log.e("STATE_TURNING_OFF","STATE_TURNING_OFF")
                    }

                    BluetoothAdapter.STATE_ON -> {

                        Log.e("STATE_ON","STATE_ON")
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {

                        Log.e("STATE_TURNING_ON","STATE_TURNING_ON")
                    }
                }
            }
        }
    };

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        if (!bt.isBluetoothEnabled) {
            bt.enable()
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                bt.autoConnect("BLIENDS")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 서비스가 종료될 때 실행
        bt.stopService()
        unregisterReceiver(mBroadcastReceiver1);
        Log.d("test", "서비스의 onDestroy")
    }

}