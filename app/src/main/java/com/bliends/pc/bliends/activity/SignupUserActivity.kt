package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.SignUpUtil
import kotlinx.android.synthetic.main.activity_signup_user.*

class SignupUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_user)

        var type = intent.getStringExtra("type")

        userSignup.setOnClickListener {
            var name: String = userName.text.toString()
            var id: String = userId.text.toString()
            var phone: String = userPhone.text.toString()
            var password: String = userPassword.text.toString()
            var cheakpassword: String = userCheakPassword.text.toString()

            var sign = SignUpUtil(this)

            if (name.isEmpty()) {
                sign.name(userName)
            }

            if (id.isEmpty()) {
                sign.id(userId)
            }

            if (phone.isEmpty()) {
                sign.phone(true,userPhone)
            }

            if (password.isEmpty()) {
                sign.password(userPassword)
            }

            if (cheakpassword.isEmpty() || password != cheakpassword) {
                sign.cheakpassword(userCheakPassword)
            }

            if (name.isNotEmpty() && id.isNotEmpty() && phone.isNotEmpty()
                    && password.isNotEmpty() && password == cheakpassword) {
                Log.e("username",name)
                Log.e("userid",id)
                Log.e("type",type)
                Log.e("phone",phone)
                Log.e("passwrod",password)
                sign.signUp(name,id,type,phone,password,this@SignupUserActivity)
            }
        }
    }
}
