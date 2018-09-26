package com.bliends.pc.bliends.util

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import org.jetbrains.anko.toast

class BlutoothService(activity: Activity, handler: Handler) {
    private val TAG: String = "BluetoothService"

    private var adapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private var acitivity: Activity = activity

    private var handler: Handler = handler


    fun getDeviceState(): Boolean {
        return if (adapter == null) {
            acitivity.toast("블루투스를 지원하지 않는 기기입니다.")
            Log.e("test", "false")
            false
        } else {
            true
        }
    }


    // 블루투스 장치의 이름이 주어졌을때 해당 블루투스 장치 객체를 페어링 된 장치 목록에서 찾아내는 코드.
    fun getDeviceFind(name: String): BluetoothDevice? {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        var selectedDevice: String = ""
        var returndevice: BluetoothDevice? = null
        var pairedDevices = adapter!!.bondedDevices
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for (device in pairedDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if (name == device.name) {
                selectedDevice = device.name
                returndevice = device
                break
            }
        }

        return if (selectedDevice == "") {
            returndevice!!
        }else if(selectedDevice != "") {
            returndevice!!
        }else{
            null
        }
    }


    fun enableBluetooth(context: Context) {
        if (adapter!!.isEnabled) {
        } else {
            TTSUtil.usingTTS(context,"앱에서 블루투스 실행 권한을 요청합니다 허용할까요?\n" +
                    "허용하시려면 예를 클릭해주세요\n")
            Log.e("test", true.toString())
            var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            acitivity.startActivityForResult(intent, 10)
        }
    }
}
