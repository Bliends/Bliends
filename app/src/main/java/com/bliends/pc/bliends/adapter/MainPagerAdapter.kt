package com.bliends.pc.bliends.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.bliends.pc.bliends.fragment.ActLogFragment
import com.bliends.pc.bliends.fragment.ChatFragment
import com.bliends.pc.bliends.fragment.LocationFragment
import com.bliends.pc.bliends.fragment.SettingFragment

class MainPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment? {
        when (position){
            0 -> return ActLogFragment.newInstance()
            1 -> return LocationFragment.newInstance()
            2 -> return ChatFragment.newInstance()
            3 -> return SettingFragment.newInstance()
        }
        return null
    }

}

class ViewPagerOnPageSelected(private val pageSelected: (Int) -> Unit = {}) : ViewPager.OnPageChangeListener {

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) { pageSelected(position) }

    override fun onPageScrollStateChanged(state: Int) {}
}