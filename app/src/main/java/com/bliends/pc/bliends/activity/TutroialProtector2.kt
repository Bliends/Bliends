package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.bliends.pc.bliends.R
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


class TutroialProtector2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_tutroial_protector2, container, false)

        return view
    }
}
