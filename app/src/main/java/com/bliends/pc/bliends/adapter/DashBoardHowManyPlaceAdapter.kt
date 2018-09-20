package com.bliends.pc.bliends.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.HowManyPlace
import org.jetbrains.anko.find

class DashBoardHowManyPlaceAdapter(private var mPlaceLog: ArrayList<HowManyPlace>)
    : RecyclerView.Adapter<DashBoardHowManyPlaceAdapter.ViewHolder>(), View.OnClickListener {

    fun add(hmp: HowManyPlace){
        mPlaceLog.add(hmp)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dash_board_how_many_place_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mPlaceLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        val view = itemView!!.find<ConstraintLayout>(R.id.place_item)
    }

    override fun onClick(v: View?) {
        // TODO("누르면 그 위치를 맵에다 와드 박아서 보여주기") //To change body of created functions use File | Settings | File Templates.
    }
}