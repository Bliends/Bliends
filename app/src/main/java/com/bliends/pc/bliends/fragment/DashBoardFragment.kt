package com.bliends.pc.bliends.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.DashBoardCurrentSituationAdapter
import com.bliends.pc.bliends.adapter.DashBoardHowManyPlaceAdapter
import com.bliends.pc.bliends.data.DashBoardLabel
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_dash_board.*
import android.graphics.Paint
import com.bliends.pc.bliends.data.ActivityLog
import com.bliends.pc.bliends.data.DashBoardDate
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.RetrofitRes
import com.bliends.pc.bliends.util.RetrofitUtil
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class DashBoardFragment : Fragment() {

    private lateinit var dashBoardCurrentSituationAdapter: DashBoardCurrentSituationAdapter
    private var mSituationLog = ArrayList<ActivityLog>()

    private lateinit var dashBoardHowManyPlaceAdapter: DashBoardHowManyPlaceAdapter
    private var mPlaceLog = ArrayList<DashBoardLabel>()

//    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNTM3NzA0NTUxfQ.fegceqw-hj0XK5iBrgBAiOoabcd1EJZUb3zwYkHOSkA"
    private var token : String? = null

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

        setGraphData()
        setLogData()

        val list = ORMUtil(context!!).tokenORM.find(Sign())
        val sign = list[list.size - 1] as Sign
        token = sign.token
        if(token == null) toast("토큰값이 없습니다!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }

    private fun setLogData() {
        RetrofitUtil.postService.getActivityLogList(token!!).enqueue(object : RetrofitRes<ArrayList<ActivityLog>>(context!!) {
            override fun callback(code: Int, body: ArrayList<ActivityLog>?) {
                if(code == 200){
                    body!!.forEach {
                        dashBoardCurrentSituationAdapter.add(it)
                    }
                }
            }
        })

        RetrofitUtil.postService.getDashBoardByLabel(token!!).enqueue(object : RetrofitRes<ArrayList<DashBoardLabel>>(context!!) {
            override fun callback(code: Int, body: ArrayList<DashBoardLabel>?) {
                if (code == 200) {
                    body!!.forEach {
                        dashBoardHowManyPlaceAdapter.add(it)
                    }
                }
            }
        })
    }

    private fun setGraphData() {
        RetrofitUtil.postService.getDashBoardByDate(token!!, 7).enqueue(object : RetrofitRes<ArrayList<DashBoardDate>>(context!!) {
            @SuppressLint("SetTextI18n")
            override fun callback(code: Int, body: ArrayList<DashBoardDate>?) {
                if (code == 200) {
                    var count = body!!.size - 1
                    val lineDataList = ArrayList<DataPoint>()
                    val barDataList = ArrayList<DataPoint>()
                    val horizontalDateList = ArrayList<String>()
                    var maxCountDate: DashBoardDate? = null
                    var maxCount = 0
                    body!!.forEach {
                        if (maxCountDate == null) maxCountDate = it
                        else {
                            if (maxCountDate!!.count!! <= it.count!!) {
                                maxCountDate = it
                                maxCount = count
                            }
                        }
                        lineDataList.add(DataPoint(count.toDouble(), it.count!! - 0.5.toDouble()))
                        barDataList.add(DataPoint(count.toDouble(), it.count!!.toDouble()))
                        horizontalDateList.add("${it.date}일")
                        count--
                    }
                    if(body.size > 1){
                        lineDataList.reverse()
                        barDataList.reverse()
                        horizontalDateList.reverse()
                        drawGraph(horizontalDateList, lineDataList, barDataList, maxCountDate!!, maxCount)
                        best_day_text.text = "최근 ${maxCountDate!!.month}/${maxCountDate!!.date} 에"
                    }else{
                        if(body.size >= 1){
                            best_day_text.text = "최근 ${maxCountDate!!.month}/${maxCountDate!!.date} 에"
                            best_day_down_text.text = "총 ${maxCountDate!!.count} 번 활동했습니다."
                            graph.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun drawGraph(horizontalDateList : ArrayList<String>,
                          lineDataList : ArrayList<DataPoint>, barDataList : ArrayList<DataPoint>, maxCountDate : DashBoardDate, maxCount : Int){

        graph.gridLabelRenderer.gridColor = Color.TRANSPARENT
        graph.gridLabelRenderer.verticalLabelsColor = Color.TRANSPARENT
        graph.gridLabelRenderer.labelHorizontalHeight = 180
        graph.gridLabelRenderer.horizontalLabelsColor = Color.argb(255, 30, 38, 64)
        graph.gridLabelRenderer.textSize = 50f
        graph.gridLabelRenderer.labelsSpace = -50

        val staticLabelsFormatter = StaticLabelsFormatter(graph)
        staticLabelsFormatter.setHorizontalLabels(horizontalDateList.toTypedArray())
        graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        val lineSeries = LineGraphSeries(lineDataList.toTypedArray())
        lineSeries.color = R.color.bottomNavColor
        lineSeries.isDrawDataPoints = true
        lineSeries.dataPointsRadius = 15f

        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 10f
        lineSeries.setCustomPaint(paint)
        lineSeries.color = R.color.bottomNavColor

        val barSeries = BarGraphSeries(barDataList.toTypedArray())
        barSeries.isDrawValuesOnTop = true
        barSeries.valuesOnTopColor = Color.argb(255, 30, 38, 64)
        barSeries.color = Color.TRANSPARENT
        barSeries.spacing = 100

        val pointSeries = PointsGraphSeries(
                arrayOf(DataPoint(maxCount.toDouble(), maxCountDate!!.count!!-0.5.toDouble())))

        pointSeries.shape = PointsGraphSeries.Shape.POINT
        pointSeries.color = Color.argb(255, 60, 179, 113)

        val pointSeries2 = PointsGraphSeries(
                arrayOf(DataPoint(maxCount.toDouble(), maxCountDate!!.count!!-0.5.toDouble())))

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
}
