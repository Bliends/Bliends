package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_tutorial_user3.*
import kotlinx.android.synthetic.main.activity_tutorial_user3.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

class TutorialUser3 : AppCompatActivity(), GestureDetector.OnGestureListener {
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
        if (p1!!.x - p0!!.x > SWIPE_MIN_DISTANCE && Math.abs(p2) > SWIPE_THRESHOLD_VELOCITY) {
            startActivity<TutorialUser2>()
            overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right)
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
        setContentView(R.layout.activity_tutorial_user3)
        TTSUtil.TutroailusingTTS(this,
                "도움요청 \n" +
                        "사용자가 도움을 요청할 경우 보호자에게 알림이 갑니다.\n" +
                        "도움요청은 메뉴를 누른 뒤 예 아니요를 선택해주세요\n" +
                        "Bliends를 시작하시려면 시작 또는 네라고 말해주세요",
                "Start",false
        )

        tutroialUser3Next.onClick {
            startActivity<UserMainActivity>()
            TTSUtil.speakStop()
            finish()
        }
    }
}