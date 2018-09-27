package com.bliends.pc.bliends.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.bliends.pc.bliends.activity.*

class TutroialAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var fragment = Fragment()
    private val MAX_PAGE = 3
    override fun getItem(position: Int): Fragment? {

            if(position<0 || MAX_PAGE<=position)
                return null


        Log.e("asdfasdf",position.toString())

        when (position) {
            0 -> {
                    fragment = TutroialProtector2()
            }

            1 -> {
                    fragment = TutroialProtector3()
            }
            2 -> {
                    fragment = TutroialProtector4()
            }
        }

        return fragment
    }

    override fun getCount(): Int {
        return MAX_PAGE
    }

}