package com.bliends.pc.bliends.socket

import android.app.Application
import io.socket.client.IO

class SocketApplication : Application() {
    companion object {
        val mSocket = IO.socket("http://norr.uy.to:5000/")
    }
}