package com.bliends.pc.bliends.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.DashBoardCurrentSituationAdapter
import com.bliends.pc.bliends.adapter.DashBoardHowManyPlaceAdapter
import com.bliends.pc.bliends.data.CurrentSituation
import com.bliends.pc.bliends.data.HowManyPlace
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_dash_board.*

class DashBoardFragment : Fragment() {

    private lateinit var dashBoardCurrentSituationAdapter : DashBoardCurrentSituationAdapter
    private var mSituationLog = ArrayList<CurrentSituation>()

    private lateinit var dashBoardHowManyPlaceAdapter : DashBoardHowManyPlaceAdapter
    private var mPlaceLog = ArrayList<HowManyPlace>()

    private var mainViewPager : ViewPager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashBoardCurrentSituationAdapter = DashBoardCurrentSituationAdapter(mSituationLog)
        current_situation_log.adapter = dashBoardCurrentSituationAdapter
        current_situation_log.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        current_situation_log.setHasFixedSize(false)
        current_situation_log.itemAnimator = DefaultItemAnimator()

        dashBoardHowManyPlaceAdapter = DashBoardHowManyPlaceAdapter(mPlaceLog)
        how_many_place_log.adapter = dashBoardHowManyPlaceAdapter
        how_many_place_log.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        how_many_place_log.setHasFixedSize(true)
        how_many_place_log.itemAnimator = DefaultItemAnimator()

        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())
        dashBoardCurrentSituationAdapter.add(CurrentSituation())

        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
        dashBoardHowManyPlaceAdapter.add(HowManyPlace())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }
}
