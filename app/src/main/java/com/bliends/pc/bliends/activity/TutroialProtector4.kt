package com.bliends.pc.bliends.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.activity_tutroial_protector4.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

class TutroialProtector4 : android.support.v4.app.Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_tutroial_protector4, container, false)

        view.tutroialProtector4Next.onClick {
            startActivity<MainActivity>()
        }

        return view
    }
}