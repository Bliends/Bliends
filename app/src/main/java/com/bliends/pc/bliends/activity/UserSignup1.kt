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
import kotlinx.android.synthetic.main.activity_user_signup1.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.regex.Pattern

class UserSignup1 : AppCompatActivity() {
    var numbercheck = false
    var numbar = "^010-\\d{4}-\\d{4}$"
    var usernumbar = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup1)

        user1Numbar.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        user1Numbar.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    if (numbercheck) {
                        user1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
                        user1NumbarLayout.setHintTextAppearance(R.style.HintError)
                        user1ErrorText.visibility = View.VISIBLE
                        user1ErrorText.text = "등록할수 없는 휴대폰번호 입니다."
                        user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
                    } else {
                        user1ErrorText.visibility = View.VISIBLE
                        user1ErrorText.text = "등록가능한 휴대폰번호 입니다."
                        user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
                        user1NumbarLayout.setHintTextAppearance(R.style.HintTextStyle)
                        user1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                        user1ErrorText.visibility = View.INVISIBLE
                    }
                    user1HintNumbar.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                user1ErrorText.visibility = View.INVISIBLE
                user1NumberView.setBackgroundResource(R.drawable.bg_login_et)
                if (user1Numbar.text.isNotEmpty())
                    user1HintNumbar.visibility = View.VISIBLE
                else
                    user1HintNumbar.visibility = View.INVISIBLE
            }
        }

        user1Name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어줌
                    userNameView.setBackgroundResource(R.drawable.bg_login_select_et)
                    userHintName.visibility = View.VISIBLE
                }, 100)
            } else {
                //흰색 배경을 표시/숨기기
                userNameView.setBackgroundResource(R.drawable.bg_login_et)
                if (user1Name.text.isNotEmpty())
                    userHintName.visibility = View.VISIBLE
                else
                    userHintName.visibility = View.INVISIBLE
            }
        }


        user1Numbar.addTextChangedListener(
                object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        if (s.isEmpty()) {
                            user1NumbarDelete.visibility = View.INVISIBLE
                        } else if (s.isNotEmpty()) {
                            user1NumbarDelete.visibility = View.VISIBLE
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
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    }

                    override fun afterTextChanged(s: Editable) {}
                })


        user1NameDelete.onClick {
            user1Name.text = null
        }

        user1NumbarDelete.onClick {
            user1Numbar.text = null
        }

        user1NextBtn.onClick {
            val numbarm = Pattern.matches(numbar, user1Numbar.text)
            if (numbarm && user1Name.text.isEmpty()) {
                numbercheck = false
                user1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                user1NumbarLayout.setHintTextAppearance(R.style.HintTextStyle)
                user1ErrorText.visibility = View.VISIBLE
                user1ErrorText.text = "등록가능한 휴대폰번호 입니다."
                user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
                toast("모든 정보를 옳바르게 기입해주세요")
            } else if (numbarm && user1Name.text.isNotEmpty()) {
                numbercheck = false
                usernumbar = user1Numbar.text.toString()
                user1NumberView.setBackgroundResource(R.drawable.bg_login_select_et)
                user1NumbarLayout.setHintTextAppearance(R.style.HintTextStyle)
                user1ErrorText.visibility = View.VISIBLE
                user1ErrorText.text = "등록가능한 휴대폰번호 입니다."
                user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorLogin))
                startActivity<ProtectorSignup2>(
                        "type" to intent.getStringExtra("type"),
                        "numbar" to usernumbar
                        )
                finish()
            } else {
                numbercheck = true
                user1NumberView.setBackgroundResource(R.drawable.bg_login_error_et)
                user1NumbarLayout.setHintTextAppearance(R.style.HintError)
                user1ErrorText.visibility = View.VISIBLE
                user1ErrorText.text = "등록할수 없는 휴대폰번호 입니다."
                user1ErrorText.setTextColor(ContextCompat.getColor(this@UserSignup1, R.color.colorError))
                user1Numbar.requestFocus()
            }

        }

        user1Back.onClick {
            startActivity<SignupSelectActivity>()
            finish()
        }
    }
}
