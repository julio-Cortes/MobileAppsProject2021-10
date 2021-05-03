package com.example.project_01.Views.Adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Interfaces.IAdapterView
import com.example.project_01.Interfaces.OnClickListener
import com.example.project_01.Models.Room
import com.example.project_01.R

class RoomAdapter(override val onClickListener: OnClickListener):
        RecyclerView.Adapter<RoomAdapter.ViewHolder>(), IAdapterView {

    var data = listOf<Room>()


    //Metodo donde crear el layout de la celda a ver
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_view, parent, false)
        return ViewHolder(view)
    }

    // Metodo que entrega información al viewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
        //Se aplica la interfaz entregada en este caso por la MainActivity
        holder.itemView.setOnClickListener {
            onClickListener.onClickItem(item)
        }
    }
    // Metodo que define el largo de la lista
    override fun getItemCount(): Int {
        return data.size
    }
    // Metodo donde agrega un item al recyclerView


    //Este metodo utiliza la lista que le entrega el viewmodel
    fun set(cases: List<Room>){
        this.data = cases
        this.notifyDataSetChanged()
    }


    // Clase interna con la definición del ViewHolder
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(item: Room){
            val  nameTextView = view.findViewById<TextView>(R.id.room_textView)
            nameTextView.text = item.name
            nameTextView.setPaintFlags(nameTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        }
    }

}