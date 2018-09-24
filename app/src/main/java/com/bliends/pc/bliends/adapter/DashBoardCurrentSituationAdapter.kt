package com.bliends.pc.bliends.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.R.layout.dash_board_current_situation_arrive
import com.bliends.pc.bliends.R.layout.dash_board_current_situation_buy
import com.bliends.pc.bliends.data.ActivityLog
import com.bliends.pc.bliends.util.AddMarkerUtil
import com.bliends.pc.bliends.util.DateStringUtil
import org.jetbrains.anko.find

class DashBoardCurrentSituationAdapter(private var mSituationLog: ArrayList<ActivityLog>)
    : RecyclerView.Adapter<DashBoardCurrentSituationAdapter.ViewHolder>() {

    private var addressText : TextView? = null
    private var dateText : TextView? = null

    fun add(a: ActivityLog) {
        mSituationLog.add(a)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dateText!!.text = DateStringUtil.replaceDate(mSituationLog[position].createdAt!!)
        if(getItemViewType(holder.adapterPosition) == 0) {
            addressText!!.text = mSituationLog[position].label!!.name + "에 도착하였습니다."
        }else{
            addressText!!.text = AddMarkerUtil.getAdress(
                    mSituationLog[position].latitude,
                    mSituationLog[position].longitude)!!.getAddressLine(0) + "\n에서 지폐를 인식했습니다."
        }

        holder.btnShowLocation.setOnClickListener {
            AddMarkerUtil.mainViewPager!!.currentItem = 1

            if (getItemViewType(holder.adapterPosition) == 0) {
                AddMarkerUtil.addWad(
                        mSituationLog[holder.adapterPosition].label!!.latitude!!.toDouble(),
                        mSituationLog[holder.adapterPosition].label!!.longitude!!.toDouble(),
                        mSituationLog[holder.adapterPosition].label!!.name + " 도착")
            } else {
                AddMarkerUtil.addWad(
                        mSituationLog[holder.adapterPosition].latitude!!.toDouble(),
                        mSituationLog[holder.adapterPosition].longitude!!.toDouble(),
                        AddMarkerUtil.getAdress(
                                mSituationLog[holder.adapterPosition].latitude,
                                mSituationLog[holder.adapterPosition].longitude)!!.getAddressLine(0) + " 에서 지폐 인식")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if(viewType == 0) dash_board_current_situation_arrive else dash_board_current_situation_buy
        val v =  LayoutInflater.from(parent.context).inflate(layout, parent, false)
        addressText = v.find(R.id.current_situation_message_two)
        dateText = v.find(R.id.current_situation_message_one)
        return ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mSituationLog[position].payments == 0) 0 else 1
    }

    override fun getItemCount(): Int = mSituationLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val btnShowLocation = itemView!!.find<TextView>(R.id.current_situation_btn)
    }
}