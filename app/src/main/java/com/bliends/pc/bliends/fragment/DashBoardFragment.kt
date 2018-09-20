package com.bliends.pc.bliends.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.DashBoardCurrentSituationAdapter
import com.bliends.pc.bliends.data.CurrentSituation
import kotlinx.android.synthetic.main.fragment_dash_board.*


class DashBoardFragment : Fragment() {

    private lateinit var dashBoardCurrentSituationAdapter : DashBoardCurrentSituationAdapter
    private var mSituationLog = ArrayList<CurrentSituation>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashBoardCurrentSituationAdapter = DashBoardCurrentSituationAdapter(mSituationLog)
        current_situation_log.adapter = dashBoardCurrentSituationAdapter
        current_situation_log.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        current_situation_log.setHasFixedSize(false)
        current_situation_log.itemAnimator = DefaultItemAnimator()

        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }
}
