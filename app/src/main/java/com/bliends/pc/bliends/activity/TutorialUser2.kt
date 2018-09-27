package com.bliends.pc.bliends.activity

import android.gesture.GestureOverlayView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.TTSUtil
import android.view.GestureDetector
import org.jetbrains.anko.startActivity


class TutorialUser2 : AppCompatActivity(), GestureDetector.OnGestureListener {
    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_MAX_OFF_PATH = 250
    private val SWIPE_THRESHOLD_VELOCITY = 200
    lateinit var gestureScanner : GestureDetector

    override fun onTouchEvent(me : MotionEvent): Boolean {
        return gestureScanner.onTouchEvent(me)
    }

    override fun onShowPress(p0: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (p0!!.x - p1!!.x > SWIPE_MIN_DISTANCE && Math.abs(p2) > SWIPE_THRESHOLD_VELOCITY) {
            startActivity<TutorialUser3>()
            overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left)
            TTSUtil.speakStop()
            finish()
        }
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureScanner = GestureDetector(this)
        setContentView(R.layout.activity_tutorial_user2)

        TTSUtil.usingTTS(this,
                "현금인식 모듈 사용법 \n" +
                        "현금 앞쪽 점자를 통해 위치를 잡고 그곳을 기준으로 \n" +
                        "왼쪽 아래 금액부분에 기기를 인식시켜주세요")
    }
}