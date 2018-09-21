package com.bliends.pc.bliends.activity

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TextInputLayout
import android.support.v7.view.ContextThemeWrapper
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_protector_signup1.*
import kotlinx.android.synthetic.main.activity_signup_protector.*
import org.jetbrains.anko.hintTextColor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor

class ProtectorSignup1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector_signup1)

        Protector1CheckNumbar.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        Protector1CheckNumbar.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintTextStyle)
                if (hasFocus) {
                    Handler().postDelayed(Runnable {
                        //흰색배경으로 덮어줌
                        protector1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                        ProtectorHintCheckNumbar.visibility = View.VISIBLE
                    }, 100)
                } else {
                    //흰색 배경을 표시/숨기기
                    protector1NumberView.setBackgroundResource(R.drawable.bg_login_et)
                    if (Protector1CheckNumbar.text.isNotEmpty())
                        ProtectorHintCheckNumbar.visibility = View.VISIBLE
                    else
                        ProtectorHintCheckNumbar.visibility = View.INVISIBLE
                }
        }

        Protector1Name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    protectorNameView.setBackgroundResource(R.drawable.bg_login_select_et)
                    ProtectorHintName.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                protectorNameView.setBackgroundResource(R.drawable.bg_login_et)
                if (Protector1Name.text.isNotEmpty())
                    ProtectorHintName.visibility = View.VISIBLE
                else
                    ProtectorHintName.visibility = View.INVISIBLE
            }
        }


        Protector1CheckNumbar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    protectorInspectionBtn.visibility = View.INVISIBLE
                } else if(s.isNotEmpty()){
                    protectorInspectionBtn.visibility = View.VISIBLE
                    protectorError.visibility = View.INVISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        })

        Protector1Name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    Protector1NameDelete.visibility = View.INVISIBLE
                } else if(s.isNotEmpty()){
                    Protector1NameDelete.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        })


        Protector1NameDelete.onClick {
            Protector1Name.text = null
        }

        protectorInspectionBtn.onClick {
            //서버연동
            protectorInspectionBtn.visibility = View.INVISIBLE//현제 버튼 없앰
            protectorError.visibility = View.VISIBLE
            protector1ErrorText.visibility = View.VISIBLE
            protector1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
            Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintError)
        }


        protector1NextBtn.onClick {
            startActivity<ProtectorSignup2>()
            finish()
        }

        protector1Back.onClick {
            startActivity<SignupSelectActivity>()
            finish()
        }
    }

}
