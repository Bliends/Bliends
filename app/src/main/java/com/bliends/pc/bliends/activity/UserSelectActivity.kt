package com.bliends.pc.bliends.activity

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import android.view.WindowManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_user_select.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class UserSelectActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        setContentView(R.layout.activity_user_select)

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorUserMain)
        }

        var a = intent.getBooleanExtra("bl",false)

        if(a){
            notext.text = "경찰서에 전화"
            yestext.text = "보호자에게 전화"
        }else{
            notext.text = "아니요"
            yestext.text = "예"
        }

        userselectYes.onClick {
            if(a) {
                var intent = intent
                intent.putExtra("bl",true)
                setResult(RESULT_OK, intent)
            }else{
                setResult(RESULT_OK, intent)
            }
            finish()
        }

        userselectNo.onClick {
            if(a){
                var intent = intent
                intent.putExtra("bl",false)
                setResult(RESULT_OK, intent)
                finish()
            }else{
                finish()
            }
        }

    }
}
