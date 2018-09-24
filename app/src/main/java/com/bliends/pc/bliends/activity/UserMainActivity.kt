package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bliends.pc.bliends.R
import android.R.menu
import android.content.Intent
import android.net.Uri
import kotlinx.android.synthetic.main.activity_user_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity


class UserMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        setSupportActionBar(userToolbar)

        userCall.onClick {
            startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:01094732771")))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.userSetting->{
                startActivity<LoginActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
