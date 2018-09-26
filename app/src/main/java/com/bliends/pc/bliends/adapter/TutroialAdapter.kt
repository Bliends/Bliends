package com.bliends.pc.bliends.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.bliends.pc.bliends.activity.*

class TutroialAdapter(fm: FragmentManager,bl : Boolean) : FragmentPagerAdapter(fm) {
    var bl = bl
    var fragment = Fragment()
    private val MAX_PAGE = 3
    override fun getItem(position: Int): Fragment? {
        if(position<0 || MAX_PAGE<=position)
            return null

        when (position) {
            0 -> {
                if(bl){
                    fragment = TutroialProtector2()
                }else{
                    fragment = TutorialUser2()
                }
            }

            1 -> {
                if(bl){
                    fragment = TutroialProtector3()
                }else{
                    fragment = TutorialUser3()
                }
            }
            2 -> {
                if(bl){
                    fragment = TutroialProtector4()
                }else{
                    Log.e("넘어x","넘어x")
                }
            }
        }
        return fragment
    }

    override fun getCount(): Int {
        return MAX_PAGE
    }

}