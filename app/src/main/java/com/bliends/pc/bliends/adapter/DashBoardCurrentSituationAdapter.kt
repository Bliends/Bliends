package com.bliends.pc.bliends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.CurrentSituation
import org.jetbrains.anko.find

class DashBoardCurrentSituationAdapter(private var mSituationLog: ArrayList<CurrentSituation>)
    : RecyclerView.Adapter<DashBoardCurrentSituationAdapter.ViewHolder>(), View.OnClickListener {

    fun add(cs: CurrentSituation){
        mSituationLog.add(cs)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnShowLocation.setOnClickListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dash_board_current_situation_item, parent, false)
        return ViewHolder(v)
    }

    //override fun getItemViewType(position: Int): Int = mSituationLog[position].type

    override fun getItemCount(): Int = mSituationLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        val btnShowLocation = itemView!!.find<TextView>(R.id.current_situation_btn)
    }


    override fun onClick(v: View?) {
        // TODO("뷰 타입 구해서 0 일 때, 로케이션 보여주고 아니면 전화 걸기") //To change body of created functions use File | Settings | File Templates.
    }
}