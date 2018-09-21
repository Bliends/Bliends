package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_protector_signup1.*
import kotlinx.android.synthetic.main.activity_protector_signup2.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class ProtectorSignup2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector_signup2)

        Protector2Id.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    protector2hintIdView.setBackgroundResource(R.drawable.bg_login_select_et)
                    protector2hintId.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                protector2hintIdView.setBackgroundResource(R.drawable.bg_login_et)
                if (Protector2Id.text.isNotEmpty()) {
                    protector2hintId.visibility = View.VISIBLE
                }else
                    protector2hintId.visibility = View.INVISIBLE
            }
        }

        protector2NextBtn.onClick {
            startActivity<ProtectorSignup3>()
            finish()
        }

        protector2Back.onClick {
            startActivity<ProtectorSignup1>()
            finish()
        }

    }
}
