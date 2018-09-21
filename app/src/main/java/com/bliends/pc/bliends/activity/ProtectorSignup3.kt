package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_protector_signup2.*
import kotlinx.android.synthetic.main.activity_protector_signup3.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class ProtectorSignup3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector_signup3)

        Protector3Pd.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    protector3hintPd.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                if (Protector3Pd.text.isNotEmpty())
                    protector3hintPd.visibility = View.VISIBLE
                else
                    protector3hintPd.visibility = View.INVISIBLE
            }
        }

        Protector3CheckPd.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    Protector3HintCheckPd.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                if (Protector3CheckPd.text.isNotEmpty())
                    Protector3HintCheckPd.visibility = View.VISIBLE
                else
                    Protector3HintCheckPd.visibility = View.INVISIBLE
            }
        }


        protector3NextBtn.onClick {
            startActivity<LoginActivity>()
            finish()
        }

        protector3Back.onClick {
            startActivity<ProtectorSignup2>()
            finish()
        }

    }
}
