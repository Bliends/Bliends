package com.bliends.pc.bliends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.Location

class LocationAdapter(private var mLocationLog: ArrayList<Location>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    fun add(l: Location){
        mLocationLog.add(l)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.location_log_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int{
        return 0
    }

    override fun getItemCount(): Int = mLocationLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}