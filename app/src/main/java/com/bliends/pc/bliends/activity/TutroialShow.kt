package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.TutroialAdapter
import kotlinx.android.synthetic.main.activity_tutroial_show.*
import android.support.v4.app.FragmentStatePagerAdapter
import com.bliends.pc.bliends.util.TTSUtil


class TutroialShow : AppCompatActivity() {
    var bl = false
    lateinit var adapter : TutroialAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutroial_show)

        var a = intent.getStringExtra("User")
        if(a == "Protector")
            bl = true
        else if(a == "User")
            bl = false

        adapter =  TutroialAdapter(supportFragmentManager,bl)
        TutroialPager.adapter = adapter
    }
}