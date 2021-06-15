package com.example.project_01.Views.Adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Deserializers.Members
import com.example.project_01.Models.Lobby
import com.example.project_01.R

class VotingAdapter ():RecyclerView.Adapter<VotingAdapter.ViewHolder>(){
    var data = mutableListOf<Members>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VotingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vote_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }



    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(item: Members){
            val  nameTextView = view.findViewById<TextView>(R.id.vote_name)
            val voteNumberTextView = view.findViewById<TextView>(R.id.vote_number)
            nameTextView.text = item.name
            nameTextView.setPaintFlags(nameTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            voteNumberTextView.text = item.vote
            voteNumberTextView.setPaintFlags(voteNumberTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        }
    }

    override fun onBindViewHolder(holder: VotingAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
    }
}