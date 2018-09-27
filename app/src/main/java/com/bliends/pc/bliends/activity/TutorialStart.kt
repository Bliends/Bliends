package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_tutorial_start.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class TutorialStart : AppCompatActivity() {
    var user = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_start)
        user = intent.getStringExtra("User")
        if(user == "User"){
        TTSUtil.usingTTS(this@TutorialStart,
                "Bliends는 시각장애인의 편의를 제공하기 위한\n" +
                        "모바일 어플리케이션 서비스입니다." +
                        "다음으로 넘어가시려면 밑에 버튼을 클릭해주세요"
               )
        }

        tutroalStart.onClick {
            if (user == "Protector") {
                startActivity<TutorialProtector1>()
                finish()
            } else if (user == "User") {
                startActivity<TutorialUser1>()
                finish()
                TTSUtil.speakStop()
            }
        }
    }
}
