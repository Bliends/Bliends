package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.SignUpUtil
import kotlinx.android.synthetic.main.activity_signup_protector.*

class SignupProtectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_protector)

        var type = intent.getStringExtra("type")

        protectorSignup.setOnClickListener {
            var name: String = protectorName.text.toString()
            var id: String = protectorId.text.toString()
            var phone: String = protectorPhone.text.toString()
            var password: String = protectorPassword.text.toString()
            var cheakpassword: String = protectorCheakPassword.text.toString()

            var sign = SignUpUtil(this)

            if (name.isEmpty()) {
                sign.name(protectorName)
            }

            if (id.isEmpty()) {
                sign.id(protectorId)
            }

            if (phone.isEmpty()) {
                sign.phone(false,protectorPhone)
            }

            if (password.isEmpty()) {
                sign.password(protectorPassword)
            }

            if (cheakpassword.isEmpty() || password != cheakpassword) {
                sign.cheakpassword(protectorCheakPassword)
            }

            if (name.isNotEmpty() && id.isNotEmpty() && phone.isNotEmpty()
                    && password.isNotEmpty() && password == cheakpassword) {
                sign.signUp(name, id, type, phone, password,this@SignupProtectorActivity)
            }
        }
    }
}
