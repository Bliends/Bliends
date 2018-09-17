package com.bliends.pc.bliends.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.HelpAdapter
import com.bliends.pc.bliends.data.Help
import kotlinx.android.synthetic.main.fragment_help.*


class HelpFragment : Fragment() {

    var mLog = ArrayList<Help>()
    lateinit var mHelpAdapter : HelpAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHelpAdapter = HelpAdapter(mLog)
        recycle_help_log.adapter = mHelpAdapter
        recycle_help_log.layoutManager = LinearLayoutManager(context)
        recycle_help_log.setHasFixedSize(false)
        recycle_help_log.itemAnimator = DefaultItemAnimator()

        mHelpAdapter.add(Help(0))
        mHelpAdapter.add(Help(1))
        mHelpAdapter.add(Help(2))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_help, container, false)

    companion object { fun newInstance() = HelpFragment() }
}
