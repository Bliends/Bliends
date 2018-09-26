package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.widget.Toolbar
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_user_main.*
import kotlinx.android.synthetic.main.activity_user_setting.*

class UserSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
        setSupportActionBar(userSettingToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //toolbar의 back키 눌렀을 때 동작
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
