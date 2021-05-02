package com.example.project_01.Models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.R

class CardAdapter: RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    var data = listOf<String>()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView = view.findViewById<TextView>(R.id.numberTextView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]

    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun update(deck: List<String>){
        data = deck
    }

}