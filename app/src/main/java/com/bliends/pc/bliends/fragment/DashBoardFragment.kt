package com.bliends.pc.bliends.fragment

import android.content.Context
import android.graphics.Color
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
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_dash_board.*
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.Log
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.bliends.pc.bliends.R.id.graph
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.DefaultLabelFormatter
import com.bliends.pc.bliends.R.id.graph
import com.bliends.pc.bliends.R.id.graph
import com.jjoe64.graphview.Viewport
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries


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


        graph.gridLabelRenderer.gridColor = Color.WHITE
        graph.gridLabelRenderer.horizontalLabelsColor = R.color.bottomNavColor
        graph.gridLabelRenderer.verticalLabelsColor = Color.TRANSPARENT
        val staticLabelsFormatter = StaticLabelsFormatter(graph)
        staticLabelsFormatter.setHorizontalLabels(arrayOf("월", "화", "수", "목", "금", "토", "일"))
        graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter
        graph.gridLabelRenderer.labelHorizontalHeight = 180

        val lineSeries = LineGraphSeries(
                arrayOf(DataPoint(0.0, 18.0),
                        DataPoint(1.0, 14.0),
                        DataPoint(2.0, 12.0),
                        DataPoint(3.0, 13.0),
                        DataPoint(4.0, 12.0),
                        DataPoint(5.0, 8.0),
                        DataPoint(6.0, 8.0)))
        lineSeries.color = R.color.bottomNavColor
        lineSeries.isDrawDataPoints = true
        lineSeries.dataPointsRadius = 15f

        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 10f
        lineSeries.setCustomPaint(paint)
        lineSeries.color = R.color.bottomNavColor

        val barSeries = BarGraphSeries(
                arrayOf(DataPoint(0.0, 19.0),
                        DataPoint(1.0, 15.0),
                        DataPoint(2.0, 13.0),
                        DataPoint(3.0, 14.0),
                        DataPoint(4.0, 13.0),
                        DataPoint(5.0, 9.0),
                        DataPoint(6.0, 9.0)))

        barSeries.isDrawValuesOnTop = true
        barSeries.valuesOnTopColor = R.color.bottomNavColor
        barSeries.color = Color.TRANSPARENT
        barSeries.spacing = 100

        val pointSeries = PointsGraphSeries(
                arrayOf(DataPoint(0.0, lineSeries.highestValueY)))

        pointSeries.shape = PointsGraphSeries.Shape.POINT
        pointSeries.color = Color.argb(255, 60, 179, 113)

        val pointSeries2 = PointsGraphSeries(
                arrayOf(DataPoint(0.0, lineSeries.highestValueY)))

        pointSeries2.shape = PointsGraphSeries.Shape.POINT
        pointSeries2.color = Color.argb(30, 60, 179, 113)
        pointSeries2.size = 45f

        graph.addSeries(lineSeries)
        graph.addSeries(barSeries)
        graph.addSeries(pointSeries)
        graph.addSeries(pointSeries2)
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.isXAxisBoundsManual = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }
}
