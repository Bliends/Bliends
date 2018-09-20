package com.bliends.pc.bliends.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import com.bliends.pc.bliends.adapter.MainPagerAdapter
import com.bliends.pc.bliends.adapter.ViewPagerOnPageSelected
import com.bliends.pc.bliends.util.AddMarkerUtil

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewPag.adapter = MainPagerAdapter(supportFragmentManager)
        mainViewPag.offscreenPageLimit = 4
        mainViewPag.addOnPageChangeListener(ViewPagerOnPageSelected(this@MainActivity::onPageSelected))

        AddMarkerUtil.mainViewPager = mainViewPag

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)

        mainViewPag.setPagingEnabled(false)

        mainBottomNav.setOnNavigationItemSelectedListener(this)

        disableShiftMode(mainBottomNav)
    }

    private fun onPageSelected(position: Int) {
        if (mainBottomNav != null) {
            when (position) {
                0 -> mainBottomNav.selectedItemId = R.id.action_dash_board
                1 -> mainBottomNav.selectedItemId = R.id.action_location
                2 -> mainBottomNav.selectedItemId = R.id.action_help
                3 -> mainBottomNav.selectedItemId = R.id.action_setting
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dash_board -> {
                mainViewPag.currentItem = 0
                window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
            }
            R.id.action_location -> {
                mainViewPag.currentItem = 1
                window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.helpNewItemBackground)
            }
            R.id.action_help -> {
                mainViewPag.currentItem = 2
                window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.helpNewItemBackground)
            }
            R.id.action_setting -> {
                mainViewPag.currentItem = 3
                window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.helpNewItemBackground)
            }
        }
        return true
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
