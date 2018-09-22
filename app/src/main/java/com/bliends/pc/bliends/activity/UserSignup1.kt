package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.UserPhoneNumbarCheck
import kotlinx.android.synthetic.main.activity_protector_signup1.*
import kotlinx.android.synthetic.main.activity_user_signup1.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.regex.Pattern

class UserSignup1 : AppCompatActivity() {
    var numbercheck = false
    var namem = false
    var oknumbercheck = false
    var okcheck = false
    var number = "^010-\\d{4}-\\d{4}$"
    var namecheck = "^(?=.*)[^\\s]{1,20}$"
    var usernumber = ""
    var username = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup1)

        user1Number.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        user1Number.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    if (numbercheck) {
                        user1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
                        user1NumberLayout.setHintTextAppearance(R.style.HintError)
                        user1ErrorText.visibility = View.VISIBLE
                        user1NumberError.visibility = View.VISIBLE
                        user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
                    } else {
                        user1ErrorText.visibility = View.VISIBLE
                        user1ErrorText.text = "등록가능한 휴대폰번호 입니다."
                        user1NumberError.visibility = View.INVISIBLE
                        user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
                        user1NumberLayout.setHintTextAppearance(R.style.HintTextStyle)
                        user1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                        if(oknumbercheck){
                            user1ErrorText.visibility = View.VISIBLE
                        }else{
                            user1ErrorText.visibility = View.INVISIBLE
                        }
                    }
                    user1HintNumbar.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                user1NumberView.setBackgroundResource(R.drawable.bg_login_et)
                user1ErrorText.visibility = View.INVISIBLE
                user1NumberError.visibility = View.INVISIBLE
                if (user1Number.text.isNotEmpty())
                    user1HintNumbar.visibility = View.VISIBLE
                else
                    user1HintNumbar.visibility = View.INVISIBLE
            }
        }

        user1Name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    if(namem){
                        userNameView.setBackgroundResource(R.drawable.bg_login_error_et)
                        user1NameLayout.setHintTextAppearance(R.style.HintError)
                        user1ErrorNameText.visibility = View.VISIBLE
                        user1ErrorNameText.text = "이름을 옳바르게 입력해 주세요"
                        user1NameError.visibility = View.VISIBLE
                        user1ErrorNameText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
                    }else {
                        user1NameLayout.setHintTextAppearance(R.style.HintTextStyle)
                        userNameView.setBackgroundResource(R.drawable.bg_login_select_et)
                        user1ErrorNameText.text = "옳바른 이름입니다"
                        user1ErrorNameText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
                        if(okcheck){
                            user1ErrorNameText.visibility = View.VISIBLE
                        }else{
                            user1ErrorNameText.visibility = View.INVISIBLE
                        }
                    }
                    userHintName.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                userNameView.setBackgroundResource(R.drawable.bg_login_et)
                user1ErrorNameText.visibility = View.INVISIBLE
                user1NameError.visibility = View.INVISIBLE
                if (user1Name.text.isNotEmpty())
                    userHintName.visibility = View.VISIBLE
                else
                    userHintName.visibility = View.INVISIBLE
            }
        }


        user1Number.addTextChangedListener(
                object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        if (s.isEmpty()) {
                            user1NumberDelete.visibility = View.INVISIBLE
                        } else if (s.isNotEmpty()) {
                            user1NumberDelete.visibility = View.VISIBLE
                            user1NumberError.visibility = View.INVISIBLE
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    }

                    override fun afterTextChanged(s: Editable) {}
                })

        user1Name.addTextChangedListener(
                object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        if (s.isEmpty()) {
                            user1NameDelete.visibility = View.INVISIBLE
                        } else if (s.isNotEmpty()) {
                            user1NameDelete.visibility = View.VISIBLE
                            user1NameError.visibility = View.INVISIBLE
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    }

                    override fun afterTextChanged(s: Editable) {}
                })


        user1NameDelete.onClick {
            user1Name.text = null
        }

        user1NumberDelete.onClick {
            user1Number.text = null
        }

        user1NextBtn.onClick {
            var numbarcheck = UserPhoneNumbarCheck(user1Number.text.toString(), this@UserSignup1)
            numbarcheck.start()
            numbarcheck.join()
            if (numbarcheck.numbarCheckData() == "존재") {
                numbercheck = true
                oknumbercheck = false
                usernumber = user1Number.text.toString()
                user1NumberError.visibility = View.VISIBLE
                user1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
                user1NumberLayout.setHintTextAppearance(R.style.HintError)
                user1ErrorText.visibility = View.VISIBLE
                user1NumberDelete.visibility = View.INVISIBLE
                user1ErrorText.text = "이미 가입되어있는 휴대폰번호 입니다."
                user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
            } else {
                val namem = Pattern.matches(namecheck, user1Name.text)
                val numberm = Pattern.matches(number, user1Number.text)
                if (numberm && namem) {
                    number()
                    name()
                    startActivity<ProtectorSignup2>(
                            "type" to intent.getStringExtra("type"),
                            "number" to usernumber,
                            "name" to username
                    )
                } else if (!numberm && !namem) {
                    notname()
                    notnumber()
                } else if (numberm && !namem) {
                    number()
                    notname()
                } else if (!numberm && namem) {
                    notnumber()
                    name()
                } else if (numberm) {
                    notnumber()
                } else if (namem) {
                    name()
                } else {
                    toast("모든 정보를 옳바르게 기입해주세요")
                }
            }
        }

        user1Back.onClick {
            startActivity<SignupSelectActivity>()
            finish()
        }
    }

    fun notnumber(){
        oknumbercheck = false
        numbercheck = true
        user1NumberDelete.visibility = View.INVISIBLE
        user1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
        user1NumberLayout.setHintTextAppearance(R.style.HintError)
        user1ErrorText.visibility = View.VISIBLE
        user1NumberError.visibility = View.VISIBLE
        user1ErrorText.text = "등록할수 없는 휴대폰번호 입니다."
        user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
    }

    fun number(){
        numbercheck = false
        oknumbercheck = true
        usernumber = user1Number.text.toString()
        user1NumberError.visibility = View.INVISIBLE
        user1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
        user1NumberLayout.setHintTextAppearance(R.style.HintTextStyle)
        user1ErrorText.visibility = View.VISIBLE
        user1ErrorText.text = "등록가능한 휴대폰번호 입니다."
        user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
    }

    fun name(){
        okcheck = true
        namem = false
        username = user1Name.text.toString()
        user1NumberError.visibility = View.INVISIBLE
        userNameView.setBackgroundResource(R.drawable.bg_login_select_et)
        user1NameLayout.setHintTextAppearance(R.style.HintTextStyle)
        user1ErrorNameText.visibility = View.VISIBLE
        user1ErrorNameText.text = "옳바른 이름입니다"
        user1ErrorNameText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
    }

    fun notname(){
        okcheck = false
        namem = true
        user1NameDelete.visibility = View.INVISIBLE
        user1NameError.visibility = View.VISIBLE
        userNameView.setBackgroundResource(R.drawable.bg_login_error_et)
        user1NameLayout.setHintTextAppearance(R.style.HintError)
        user1ErrorNameText.visibility = View.VISIBLE
        user1ErrorNameText.text = "이름을 옳바르게 입력해 주세요"
        user1ErrorNameText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
    }
}
