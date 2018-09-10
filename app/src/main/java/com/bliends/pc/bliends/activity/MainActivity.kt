package com.bliends.pc.bliends.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.bliends.pc.bliends.adapter.MainPagerAdapter
import com.bliends.pc.bliends.adapter.ViewPagerOnPageSelected
import com.bliends.pc.bliends.util.TTSUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewPag.adapter = MainPagerAdapter(supportFragmentManager)

        mainViewPag.addOnPageChangeListener(ViewPagerOnPageSelected(this@MainActivity::onPageSelected))

        mainBottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_activity_log -> mainViewPag.currentItem = 0
                R.id.action_location -> mainViewPag.currentItem = 1
                R.id.action_chat -> mainViewPag.currentItem = 2
                R.id.action_setting -> mainViewPag.currentItem = 3
            }
            return@setOnNavigationItemSelectedListener true
        }

        disableShiftMode(mainBottomNav)
    }

    private fun onPageSelected(position: Int) {
        if(mainBottomNav != null){
            when(position){
                0 -> mainBottomNav.selectedItemId = R.id.action_activity_log
                1 -> mainBottomNav.selectedItemId = R.id.action_location
                2 -> mainBottomNav.selectedItemId = R.id.action_chat
                3 -> mainBottomNav.selectedItemId = R.id.action_setting
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView

                item.setShiftingMode(false)
                // set once again checked value, so view will be updated

                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("BNVHelper", "Unable to get shift mode field", e)
        } catch (e: IllegalAccessException) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e)
        }
    }
}
