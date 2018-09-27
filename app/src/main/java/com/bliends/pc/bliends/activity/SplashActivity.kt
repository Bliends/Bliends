package com.bliends.pc.bliends.activity

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.google.firebase.messaging.FirebaseMessaging
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {
    var token: String? = null
    var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        try {
            var list: List<Any> = ORMUtil(this@SplashActivity).tokenORM.find(Sign())
            var userlist : List<Any> = ORMUtil(this@SplashActivity).userORM.find(User())
            var user  = userlist[userlist.size -1] as User
            var sign = list[list.size - 1] as Sign
            token = sign.token
            type = user.type
            FirebaseMessaging.getInstance().subscribeToTopic(user._id)
            Log.e("token", token)
        } catch (e: Exception) {
            token = null
        }

        Handler().postDelayed({
            if (token != null) {
                toast("자동로그인 완료 되었습니다.")
                if(type == "P"){
                    startActivity<UserMainActivity>()
                    finish()
                }else{
                    startActivity<MainActivity>()
                    finish()
                }
            } else {
                toast("로그인이 필요한 서비스 입니다.")
                startActivity<LoginActivity>()
                finish()
            }
        }, 2000)
    }
}
