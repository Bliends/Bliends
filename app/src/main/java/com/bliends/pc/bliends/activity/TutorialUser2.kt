package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.TTSUtil

class TutorialUser2 : android.support.v4.app.Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_tutorial_user2, container, false)


        TTSUtil.usingTTS(context!!,
                "현금인식 모듈 사용법 \n" +
                        "현금 앞쪽 점자를 통해 위치를 잡고 그곳을 기준으로 \n" +
                        "왼쪽 아래 금액부분에 기기를 인식시켜주세요")

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        TTSUtil.speakStop()
    }
}