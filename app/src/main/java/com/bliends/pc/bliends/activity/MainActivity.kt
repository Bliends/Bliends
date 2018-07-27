package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener {
            ORMUtil(this@MainActivity).tokenORM.delete(Sign())
            ORMUtil(this@MainActivity).userORM.delete(User())
            startActivity<LoginActivity>()
            toast("로그아웃")
            finish()
        }
    }
}
