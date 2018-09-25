package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bliends.pc.bliends.R
import android.R.menu
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.util.ORMUtil
import kotlinx.android.synthetic.main.activity_user_main.*
import org.jetbrains.anko.startActivity


class UserMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        setSupportActionBar(userToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.userSetting->{
                //테스트로 로그인 부분으로 넘어가게 해놨음
                ORMUtil(this).tokenORM.delete(Sign())
                ORMUtil(this).userORM.delete(User())
                startActivity<LoginActivity>()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
