package com.example.androidapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VolumeStatAdapter(private val volumeList: List<Int>) : RecyclerView.Adapter<VolumeStatAdapter.VolumeStatViewHolder>() {

    inner class VolumeStatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val volumeText: TextView = itemView.findViewById(R.id.volumeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumeStatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_helloact1, parent, false)
        return VolumeStatViewHolder(view)
    }

    override fun onBindViewHolder(holder: VolumeStatViewHolder, position: Int) {
        val volume = volumeList[position]
        holder.volumeText.text = "Volume: $volume"
    }

    override fun getItemCount(): Int {
        return volumeList.size
    }
}
