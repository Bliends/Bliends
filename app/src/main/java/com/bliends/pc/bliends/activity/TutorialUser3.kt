package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_tutorial_user3.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

class TutorialUser3 : android.support.v4.app.Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_tutorial_user3, container, false)

        TTSUtil.TutroailusingTTS(context!!,
                "도움요청 \n" +
                        "사용자가 도움을 요청할 경우 보호자에게 알림이 갑니다.\n" +
                        "도움요청은 메뉴를 누른 뒤 예,아니요를 선택해주세요\n" +
                        "BLIENDS를 시작하시려면 시작 또는 네라고 말해주세요",
                "end"
        ,false
        )

        view.tutroialUser3Next.onClick {
            startActivity<UserMainActivity>()
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        TTSUtil.speakStop()
    }
}