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
    lateinit var adapter : TutroialAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutroial_show)

        adapter =  TutroialAdapter(supportFragmentManager)
        TutroialPager.adapter = adapter
    }
}