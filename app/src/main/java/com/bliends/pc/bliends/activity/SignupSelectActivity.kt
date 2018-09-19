package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_signup_select.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class SignupSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_select)

        selectBack.onClick {
            startActivity<LoginActivity>()
            finish()
        }

        userBtn.setOnClickListener {
            startActivity<SignupUserActivity>("type" to "P")
                    finish()
        }

        protectorBtn.setOnClickListener {
            startActivity<SignupProtectorActivity>("type" to "C")
            finish()
        }
    }
}
