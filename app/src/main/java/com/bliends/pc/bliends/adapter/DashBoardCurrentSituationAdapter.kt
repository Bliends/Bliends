package com.bliends.pc.bliends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.CurrentSituation

class DashBoardCurrentSituationAdapter(private var mSituationLog: ArrayList<CurrentSituation>)
    : RecyclerView.Adapter<DashBoardCurrentSituationAdapter.ViewHolder>() {

    fun add(cs: CurrentSituation){
        mSituationLog.add(cs)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dash_board_current_situation_item, parent, false)
        return ViewHolder(v)
    }

    //override fun getItemViewType(position: Int): Int = mSituationLog[position].type

    override fun getItemCount(): Int = mSituationLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}