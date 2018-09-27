package com.bliends.pc.bliends.activity

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.BlutoothService
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_tutorial_user1.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.InputStream

class TutorialUser1 : AppCompatActivity() {
    private var btService: BlutoothService? = null
    var bt: BluetoothSPP = BluetoothSPP(this)
    var adapter = BluetoothAdapter.getDefaultAdapter()
    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_user1)

        if (btService == null) {
            btService = BlutoothService(this, handler)
        }

        TTSUtil.usingTTS(this@TutorialUser1,
                "블루투스기기 설정 \n" +
                        "블루투스 설정창에서  Bliends  를 검색해주시고\n" +
                        "페어링을 위해 pin번호 0 0 0 0을 입력해주세요\n" +
                        "블루투스를 설정하시려면 아래 버튼을 눌러주세요\n" +
                        "블루투스 설정은 보호자와 함께 설정하길 추천드립니다.\n"
        )

        bt!!.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
            }

            override fun onDeviceConnected(name: String?, address: String?) {
                toast("기기와 연걸이 정상적으로 완료되었습니다.")
                TTSUtil.usingTTS(this@TutorialUser1, "기기와 연걸이 정상적으로 완료되었습니다.\n 다음으로 이동합니다.")
                startActivity<TutroialShow>("User" to "User")
                finish()
            }

            override fun onDeviceConnectionFailed() {
                toast("기기와 연걸이 실패하였습니다.")
            }
        })

        tutroaluserBluetooth.onClick {
            if (btService!!.getDeviceState()) {
                // 블루투스가 지원 가능한 기기일 때
                btService!!.enableBluetooth(this@TutorialUser1)
            } else {
                finish()
            }
        }

        tutroialNext.onClick {
            if (!bt.isBluetoothEnabled) {
                toast("블루투스를 설정하셔야 다음으로 넘어갈수 있습니다.")
                TTSUtil.usingTTS(this@TutorialUser1, "블루투스를 설정하셔야 다음으로 넘어갈수 있습니다.")
            } else {
                if (!bt.isServiceAvailable) {
                    startActivity<TutorialUser2>("User" to "User")
                    finish()
                    TTSUtil.speakStop()
                }
            }
        }
    }

    private var mReceiver = object : BroadcastReceiver() { //각각의 디바이스로부터 정보를 받으려면 만들어야함
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                Log.e(device.name, device.address)
//                BLIENDS
                var mRemoteDevie: BluetoothDevice = device


                if (device.name == "BLIENDS") {
                    Log.e("bluetooth","asdfsadf")
                    // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
                    val uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00895f9b34fb")
                    try {
                        Log.e("bluetooth","연결")
                        // 소켓 생성, RFCOMM 채널을 통한 연결.
                        // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와 통신할 수 있는 소켓을 생성함.
                        // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는 BluetoothSocket 오브젝트를 리턴함.
                        var mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid)
                        mSocket.connect() // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.
                        TTSUtil.usingTTS(this@TutorialUser1, "블루투스가 정상적으로 연결되었습니다. 다음으로 넘어갑니다.")
                        startActivity<TutorialUser2>()
                        finish()
                    } catch (e: Exception) { // 블루투스 연결 중 오류 발생
                        Toast.makeText(this@TutorialUser1, "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED == action) {
                TTSUtil.usingTTS(this@TutorialUser1, "블루투스기기를 찾고 있습니다. 잠시만 기다려주세요")
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("반환", "1234")
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("asdf", "asdf")

                TTSUtil.usingTTS(this@TutorialUser1, "블루투스가 켜졌습니다.\n 기기를 검색합니다.\n 잠시만 기다려주세요")
                adapter.startDiscovery()
                var filter = IntentFilter()
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED) //BluetoothAdapter.ACTION_DISCOVERY_STARTED : 블루투스 검색 시작
                filter.addAction(BluetoothDevice.ACTION_FOUND) //BluetoothDevice.ACTION_FOUND : 블루투스 디바이스 찾음
                registerReceiver(mReceiver, filter)
                Log.e("null", "null")
            }
        } else {
            toast("블루투스를 켜주세요.")

            TTSUtil.usingTTS(this@TutorialUser1, "블루투스가 켜주세요.")
        }
    }


}
