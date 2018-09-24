package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.UserPhoneNumbarCheck
import kotlinx.android.synthetic.main.activity_protector_signup1.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.regex.Pattern

class ProtectorSignup1 : AppCompatActivity() {
    var numbercheck = false
    var okcheck = false
    var name = false
    var nameokcheck = false
    var protectorname = ""
    var protectornumber = ""
    var namecheck = "^(?=.*)[^\\s]{1,20}$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_protector_signup1)

        Protector1CheckNumbar.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        Protector1CheckNumbar.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    if (numbercheck) {
                        protector1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
                        Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintError)
                        protectorError.visibility = View.VISIBLE
                        protector1ErrorText.visibility = View.VISIBLE
                    } else {
                        Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintTextStyle)
                        protector1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                        if (okcheck) {
                            protector1ErrorText.visibility = View.VISIBLE
                        } else {
                            protector1ErrorText.visibility = View.INVISIBLE

                        }

                    }
                    ProtectorHintCheckNumbar.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                protectorError.visibility = View.INVISIBLE
                protector1ErrorText.visibility = View.INVISIBLE
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
                    if (name) {
                        protector1ErrorNametext.visibility = View.VISIBLE
                        protectorNameError.visibility = View.VISIBLE
                        protector1ErrorNametext.text = "이름을 옳바르게 입력해 주세요"
                        protector1ErrorNametext.setTextColor(ContextCompat.getColor(this@ProtectorSignup1, R.color.colorError))
                        protectorNameView.setBackgroundResource(R.drawable.bg_login_error_et)
                        Protector1NameLayout.setHintTextAppearance(R.style.HintError)
                        protectorNameView.setBackgroundResource(R.drawable.bg_login_error_et)
                    } else {
                        protectorNameView.setBackgroundResource(R.drawable.bg_login_select_et)
                        Protector1NameLayout.setHintTextAppearance(R.style.HintTextStyle)
                        if (nameokcheck) {
                            protector1ErrorNametext.visibility = View.VISIBLE
                        } else {
                            protector1ErrorNametext.visibility = View.INVISIBLE
                        }
                    }


                    ProtectorHintName.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                protectorNameView.setBackgroundResource(R.drawable.bg_login_et)
                protector1ErrorNametext.visibility = View.INVISIBLE
                protectorNameError.visibility = View.INVISIBLE
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
                } else if (s.isNotEmpty()) {
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
                } else if (s.isNotEmpty()) {
                    Protector1NameDelete.visibility = View.VISIBLE
                    protectorNameError.visibility = View.INVISIBLE
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
            var numbarcheck = UserPhoneNumbarCheck(Protector1CheckNumbar.text.toString(), this@ProtectorSignup1)
            numbarcheck.start()
            numbarcheck.join()
            if (numbarcheck.numbarCheckData() != "존재") {
                numbercheck = true
                okcheck = false
                protectorInspectionBtn.visibility = View.INVISIBLE
                protectorError.visibility = View.VISIBLE
                protector1ErrorText.visibility = View.VISIBLE
                protector1ErrorText.text = "해당 사용자의 휴대폰번호가 존재하지 않습니다."
                protector1ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup1, R.color.colorError))
                protector1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
                Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintError)
            } else {
                numbercheck = false
                okcheck = true
                protectornumber = Protector1CheckNumbar.text.toString()
                protector1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintTextStyle)
                protector1ErrorText.visibility = View.VISIBLE
                protector1ErrorText.text = "사용자의 휴대폰 번호가 확인되었습니다."
                protector1ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup1, R.color.colorLogin))
            }
        }

        protector1NextBtn.onClick {
            val numbarm = Pattern.matches(namecheck, Protector1Name.text)

            if (okcheck && numbarm) {
                noterrorname()
                Log.e("name",protectorname)
                Log.e("number",protectornumber)
                startActivity<ProtectorSignup2>(
                        "type" to intent.getStringExtra("type"),
                        "number" to protectornumber,
                        "name" to protectorname
                )
                finish()
            } else if (!okcheck && !numbarm) {
                errorphone()
                errorname()
            } else if (!numbarm) {
                errorname()
            } else if (numbarm && !okcheck) {
                noterrorname()
                errorphone()
            } else if (numbarm) {
                noterrorname()
            } else if (!okcheck) {
                errorphone()
            } else {
                toast("모든 정보를 옳바르게 기입해주세요")
            }
        }
        protector1Back.onClick {
            startActivity<SignupSelectActivity>()
            finish()
        }
    }
    fun noterrorname() {
        name = false
        protectorname = Protector1Name.text.toString()
        nameokcheck = true
        protector1ErrorNametext.visibility = View.VISIBLE
        protectorInspectionBtn.visibility = View.INVISIBLE
        protector1ErrorNametext.text = "옳바른 이름입니다"
        Protector1Name.requestFocus()
        protector1ErrorNametext.setTextColor(ContextCompat.getColor(this@ProtectorSignup1, R.color.colorLogin))
        protectorNameView.setBackgroundResource(R.drawable.bg_login_select_et)
        Protector1NameLayout.setHintTextAppearance(R.style.HintTextStyle)
        Protector1Name.requestFocus()
    }
    fun errorname(){
        name = true
        nameokcheck = false
        Protector1NameDelete.visibility = View.INVISIBLE
        protector1ErrorNametext.visibility = View.VISIBLE
        protectorInspectionBtn.visibility = View.INVISIBLE
        protectorNameError.visibility = View.VISIBLE
        protector1ErrorNametext.text = "이름을 옳바르게 입력해 주세요"
        protector1ErrorNametext.setTextColor(ContextCompat.getColor(this@ProtectorSignup1, R.color.colorError))
        protectorNameView.setBackgroundResource(R.drawable.bg_login_error_et)
        Protector1NameLayout.setHintTextAppearance(R.style.HintError)
        Protector1Name.requestFocus()
    }

    fun errorphone(){
        numbercheck = true
        okcheck = false
        protectorInspectionBtn.visibility = View.INVISIBLE
        protectorError.visibility = View.VISIBLE
        protector1ErrorText.visibility = View.VISIBLE
        protector1ErrorText.text = "해당 사용자의 휴대폰번호가 존재하지 않습니다."
        protector1ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup1, R.color.colorError))
        protector1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
        Protector1CheckNumbarLayout.setHintTextAppearance(R.style.HintError)
    }
}
