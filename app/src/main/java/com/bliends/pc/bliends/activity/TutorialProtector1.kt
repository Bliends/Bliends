package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.fragment.AddLabelDialogFragment
import kotlinx.android.synthetic.main.activity_tutorial_protector1.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class TutorialProtector1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_protector1)

        tutroalProtectorLabel.onClick {
            //집설정 팝업에 Edittext에서 장소를 적지말고 Textview로 집이라고 써주삼
            //그리고 가능하면 집설정하고 더 설정하시겠습니까? 이런거 뜨면서 원래 기존 팝업뜨는것도 괜찮을듯
            val addHomeLabel = AddLabelDialogFragment.newInstance(true)
            addHomeLabel.isCancelable = false
            addHomeLabel.show(fragmentManager, "add_home_label_dialog")
        }
    }
}
