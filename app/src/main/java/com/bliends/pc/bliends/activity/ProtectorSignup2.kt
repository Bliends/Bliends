package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.Overlap
import kotlinx.android.synthetic.main.activity_protector_signup1.*
import kotlinx.android.synthetic.main.activity_protector_signup2.*
import kotlinx.android.synthetic.main.activity_protector_signup3.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.regex.Pattern

class ProtectorSignup2 : AppCompatActivity() {
    var type :String? = null
    var name :String? = null
    var number :String? = null
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector_signup2)

        type = intent.getStringExtra("type")
        if(type == "C"){
            signup2.text = "보호자 회원가입(2/3)"
        }else if(type == "P"){
            signup2.text = "사용자 회원가입(2/3)"
        }
        name = intent.getStringExtra("name")
        number = intent.getStringExtra("number")
        Log.e(name,number)
        var overlapcheck = false
        var idcheck = "^(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{8,20}$"
        Protector2Id.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    if (overlapcheck) {
                        protector2Error.visibility = View.VISIBLE
                        protector2ErrorText.visibility = View.VISIBLE
                        Protector2IdLayout.setHintTextAppearance(R.style.HintTextStyle)
                        protector2hintIdView.setBackgroundResource(R.drawable.bg_login_error_et)
                    } else {
                        Protector2IdLayout.setHintTextAppearance(R.style.HintTextStyle)
                        protector2hintIdView.setBackgroundResource(R.drawable.bg_login_select_et)
                    }
                    protector2hintId.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                protector2Error.visibility = View.INVISIBLE
                protector2ErrorText.visibility = View.INVISIBLE
                protector2hintIdView.setBackgroundResource(R.drawable.bg_login_et)
                if (Protector2Id.text.isNotEmpty()) {
                    protector2hintId.visibility = View.VISIBLE
                } else
                    protector2hintId.visibility = View.INVISIBLE
            }
        }

        Protector2Id.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    protector2InspectionBtn.visibility = View.INVISIBLE
                } else if(s.isNotEmpty()){
                    protector2InspectionBtn.visibility = View.VISIBLE
                    protector2Error.visibility = View.INVISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        })

        protector2InspectionBtn.onClick {
            val emailm = Pattern.matches(idcheck, Protector2Id.text)
            if (!emailm) {
                overlapcheck = false
                protector2InspectionBtn.visibility = View.INVISIBLE
                protector2InspectionBtn.visibility = View.INVISIBLE
                protector2Error.visibility = View.VISIBLE
                protector2ErrorText.visibility = View.VISIBLE
                protector2ErrorText.text = "8 ~ 20자의 영문, 숫자로 이루어진 암호를 기입해주세요."
                Protector2IdLayout.setHintTextAppearance(R.style.HintError)
                protector2ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup2, R.color.colorError))
                protector2hintIdView.setBackgroundResource(R.drawable.bg_login_error_et)
            } else if (emailm) {
                var overlap = Overlap(Protector2Id.text.toString(), this@ProtectorSignup2)
                overlap.start()
                overlap.join()
                Log.e("test", overlap.OverlapData())
                if (overlap.OverlapData() == "중복") {
                    overlapcheck = false
                    protector2InspectionBtn.visibility = View.INVISIBLE
                    protector2Error.visibility = View.VISIBLE
                    protector2ErrorText.visibility = View.VISIBLE
                    protector2ErrorText.text = "해당 아이디가 이미 존재합니다."
                    protector2ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup2, R.color.colorError))
                    Protector2IdLayout.setHintTextAppearance(R.style.HintError)
                    protector2hintIdView.setBackgroundResource(R.drawable.bg_login_error_et)
                } else {
                    id = Protector2Id.text.toString()
                    overlapcheck = true
                    protector2Error.visibility = View.INVISIBLE
                    protector2ErrorText.visibility = View.VISIBLE
                    protector2ErrorText.text = "사용 가능한 아이디 입니다."
                    protector2ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup2, R.color.colorLogin))
                    Protector2IdLayout.setHintTextAppearance(R.style.HintTextStyle)
                    protector2hintIdView.setBackgroundResource(R.drawable.bg_login_select_et)
                }
            }
        }
            protector2NextBtn.onClick {

                Log.e("over",overlapcheck.toString())
                if (overlapcheck && Protector2Id.text.toString().isNotEmpty()) {
                    startActivity<ProtectorSignup3>(
                            "type" to type,
                            "number" to number,
                            "name" to name,
                            "id" to id
                    )
                    finish()
                }else{
                    toast("모든 정보를 옳바르게 기입해주세요")
                }
            }

            protector2Back.onClick {
                startActivity<ProtectorSignup1>()
                finish()
            }

    }
}
