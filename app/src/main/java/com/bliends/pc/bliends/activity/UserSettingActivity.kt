package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.widget.Toolbar
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.fragment.ChangePhoneDialogFragment
import com.bliends.pc.bliends.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_user_main.*
import kotlinx.android.synthetic.main.activity_user_setting.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class UserSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
        setSupportActionBar(userSettingToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user_bluetooth_settings.onClick {
            val changePhoneFragment = ChangePhoneDialogFragment()
            changePhoneFragment.show(this@UserSettingActivity.fragmentManager, "add_dialog")
        }

        user_phone_settings.onClick {
        val changePhoneFragment = ChangePhoneDialogFragment()
        changePhoneFragment.show(this@UserSettingActivity.fragmentManager, "add_dialog")
        }
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
