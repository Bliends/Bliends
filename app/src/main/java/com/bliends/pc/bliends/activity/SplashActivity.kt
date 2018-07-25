package com.bliends.pc.bliends.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.bliends.pc.bliends.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var token: String
        var pres = getSharedPreferences("pres", Context.MODE_PRIVATE)
        token = pres.getString("token","")

        Handler().postDelayed({
            if(token != null){
                Toast.makeText(applicationContext, "자동로그인 완료 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(applicationContext, "로그인이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        },3000)
    }
}
