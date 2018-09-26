package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_tutorial_user1.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class TutorialUser1 : AppCompatActivity() {
    var bt: BluetoothSPP = BluetoothSPP(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_user1)

        TTSUtil.usingTTS(this@TutorialUser1,
                "블루투스기기 설정 \n" +
                "블루투스 설정창에서  Bliends를 검색해주시고\n" +
                "페어링을 위해 pin번호 0000을 입력해주세요\n" +
                "블루투스를 설정하시려면 아래 버튼을 눌러주세요")

        tutroaluserBluetooth.onClick{
            if (!bt.isBluetoothEnabled) {
                bt.enable()
                toast("블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
                TTSUtil.usingTTS(this@TutorialUser1, "블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
                Log.e("isServiceAvailable","노연결")
            } else {
                if (!bt.isServiceAvailable) {
                    bt.setupService()
                    bt.startService(BluetoothState.DEVICE_OTHER)
                    bt.autoConnect("BLIENDS")
                    Log.e("isServiceAvailable","연결")
                }
            }
        }

        tutroialNext.onClick {
            if (!bt.isBluetoothEnabled) {
                toast("블루투스를 설정하셔야 다음으로 넘어갈수 있습니다.")
                TTSUtil.usingTTS(this@TutorialUser1, "블루투스를 설정하셔야 다음으로 넘어갈수 있습니다.")
            }else{
                if (!bt.isServiceAvailable) {
                    startActivity<TutroialShow>("User" to "User")
                    finish()
                }
            }
        }

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
    }

    override fun onDestroy() {
        super.onDestroy()
        TTSUtil.speakStop()
    }
}
