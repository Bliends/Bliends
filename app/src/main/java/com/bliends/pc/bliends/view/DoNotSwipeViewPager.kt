package com.bliends.pc.bliends.view

import android.content.Context
import android.view.MotionEvent
import android.support.v4.view.ViewPager
import android.util.AttributeSet


class DoNotSwipeViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var enabled: Boolean? = false

    init {
        this.enabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.enabled!!) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.enabled!!) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    fun setPagingEnabled(enabled: Boolean) {
        this.enabled = enabled
    }
}

