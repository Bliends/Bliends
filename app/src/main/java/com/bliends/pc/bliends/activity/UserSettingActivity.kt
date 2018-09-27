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
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.fragment.ChangePhoneDialogFragment
import com.bliends.pc.bliends.fragment.SettingFragment
import com.bliends.pc.bliends.util.BlutoothService
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_user_main.*
import kotlinx.android.synthetic.main.activity_user_setting.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class UserSettingActivity : AppCompatActivity() {
    private var btService: BlutoothService? = null
    var bt: BluetoothSPP = BluetoothSPP(this)
    lateinit var mSocket : BluetoothSocket
    var adapter = BluetoothAdapter.getDefaultAdapter()
    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
        setSupportActionBar(userSettingToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user_logout_settings.onClick {
            ORMUtil(this@UserSettingActivity).tokenORM.delete(Sign())
            ORMUtil(this@UserSettingActivity).userORM.delete(User())
            startActivity<LoginActivity>()
            toast("로그아웃")
            finish()
        }

        if (btService == null) {
            btService = BlutoothService(this, handler)
        }

        user_bluetooth_settings.onClick {
            tts("블루투스 설정이 정상적으로 완료되었습니다.")
            if (btService!!.getDeviceState()) {
                // 블루투스가 지원 가능한 기기일 때
                btService!!.enableBluetooth(this@UserSettingActivity)
            } else {
                finish()
            }
        }



        user_phone_settings.onClick {
            tts("보호자 휴대폰번호 변경하기 입니다.\n 변경하지 않으시려면 뒤로가기를 눌러주세요")
            val changePhoneFragment = ChangePhoneDialogFragment("User")
            changePhoneFragment.show(this@UserSettingActivity.fragmentManager, "add_dialog")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //toolbar의 back키 눌렀을 때 동작
                finish()
                tts("메인으로 이동.")
                toast("메인으로 이동합니다.")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var mReceiver = object : BroadcastReceiver() { //각각의 디바이스로부터 정보를 받으려면 만들어야함
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                Log.e(device.name, device.address)
//                BLIENDS

                if (device.name == "BLIENDS") {
                    Log.e("bluetooth", "asdfsadf")
                    connectToSelectedDevice(device)
                }
            }
        }
    }

    internal fun connectToSelectedDevice(selectedDeviceName: BluetoothDevice) {

        adapter.cancelDiscovery()
        unregisterReceiver(mReceiver)
        // BluetoothDevice 원격 블루투스 기기를 나타냄.
        var mRemoteDevie: BluetoothDevice = selectedDeviceName
        // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
        val uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결.
            // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와 통신할 수 있는 소켓을 생성함.
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는 BluetoothSocket 오브젝트를 리턴함.
            mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid)

            mSocket.connect() // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

            startActivity<UserMainActivity>()
            finish()

        } catch (e: Exception) { // 블루투스 연결 중 오류 발생
            Log.e("e",e.toString())
            Toast.makeText(this@UserSettingActivity, "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            TTSUtil.usingTTS(this@UserSettingActivity, "블루투스 연결 중 오류가 발생했습니다.\n 블루투스 설정창에서 직접 Bliends를 연결해주세요")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("반환", "1234")
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("asdf", "asdf")

                TTSUtil.usingTTS(this, "블루투스가 켜졌습니다.")
                adapter.startDiscovery()
                var filter = IntentFilter()
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED) //BluetoothAdapter.ACTION_DISCOVERY_STARTED : 블루투스 검색 시작
                filter.addAction(BluetoothDevice.ACTION_FOUND) //BluetoothDevice.ACTION_FOUND : 블루투스 디바이스 찾음
                registerReceiver(mReceiver, filter)
                Log.e("null", "null")
            }
        } else {
            toast("블루투스를 켜주세요.")
            TTSUtil.usingTTS(this, "블루투스를 켜주세요.")
        }
    }

    fun tts(message: String) {
        TTSUtil.usingTTS(this@UserSettingActivity, message)
    }
}
