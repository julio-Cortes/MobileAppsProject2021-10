package com.example.project_01.Views.Adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Interfaces.IAdapterView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.Models.Lobby
import com.example.project_01.R

class LobbyAdapter(override val onClickListener: OnClickListener ):
        RecyclerView.Adapter<LobbyAdapter.ViewHolder>(), IAdapterView {

    var data = mutableListOf<LobbyCredentials>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClickItem(item)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }


    fun set(lobbys: MutableList<LobbyCredentials>){
        this.data = lobbys
        this.notifyDataSetChanged()
    }


    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(item: LobbyCredentials){
            val  nameTextView = view.findViewById<TextView>(R.id.room_textView)
            nameTextView.text = item.name
            nameTextView.setPaintFlags(nameTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        }
    }

}