package com.bliends.pc.bliends.adapter

import android.annotation.SuppressLint
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.DashBoardLabel
import com.bliends.pc.bliends.util.AddMarkerUtil
import kotlinx.android.synthetic.main.dash_board_how_many_place_item.view.*
import org.jetbrains.anko.find

class DashBoardHowManyPlaceAdapter(private val mPlaceLog: ArrayList<DashBoardLabel>)
    : RecyclerView.Adapter<DashBoardHowManyPlaceAdapter.ViewHolder>() {

    fun add(dbl: DashBoardLabel){
        mPlaceLog.add(dbl)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rank = position + 1
        holder.itemView.how_many_place_rank.text = "$rank 위"
        holder.itemView.how_many_place_label_text.text = mPlaceLog[position].label!!.name

        holder.view.setOnClickListener {
            AddMarkerUtil.mainViewPager!!.currentItem = 1
            AddMarkerUtil.addWad(
                    mPlaceLog[holder.adapterPosition].label!!.latitude!!.toDouble(),
                    mPlaceLog[holder.adapterPosition].label!!.longitude!!.toDouble(),
                    mPlaceLog[holder.adapterPosition].label!!.name!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dash_board_how_many_place_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mPlaceLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        val view = itemView!!.find<ConstraintLayout>(R.id.place_item)
    }
}