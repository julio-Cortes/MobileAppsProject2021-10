package com.example.project_01.Views.Adapters

import android.graphics.Color
import android.graphics.Paint
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Deserializers.Members
import com.example.project_01.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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
            val location_offline = view.findViewById<TextView>(R.id.location_offline)
            val linear_principal = view.findViewById<FrameLayout>(R.id.linear_principal)
            nameTextView.text = item.name
            nameTextView.setPaintFlags(nameTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            voteNumberTextView.text = item.vote
            voteNumberTextView.setPaintFlags(voteNumberTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

            if(item.location?.timestamp != null){
                var date: Date? = null
                val formatter_location = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzzz")
                val formatter_today = SimpleDateFormat("MMM dd, yyyy HH:mm:ss")
                item.location.timestamp.replace("(Coordinated Universal Time)","")
                val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
                try {
                    date = formatter_location.parse(item.location.timestamp)
                    val today = formatter_today.parse(currentDateTimeString)
                    val minutes = ((today.time - date.time)/(1000*60))%60
                    if( minutes < 1 ) {
                        linear_principal.setBackgroundResource(R.drawable.border)
                        nameTextView.setTextColor(Color.parseColor("#846B8A"))
                        voteNumberTextView.setTextColor(Color.parseColor("#846B8A"))
                        location_offline.setTextColor(Color.parseColor("#846B8A"))
                        //Aqui hay que poner la direccion
                        val geocoder: Geocoder
                        val direccion: List<Address>
                        geocoder = Geocoder(view.context, Locale.getDefault())
                        direccion = geocoder.getFromLocation(
                            item.location.lat!!.toDouble(),
                            item.location.long!!.toDouble(),
                            1
                        )
                        val address: String = direccion[0].getAddressLine(0)
                        location_offline.text = address
                    }
                    else{
                        linear_principal.setBackgroundColor(Color.LTGRAY)
                        nameTextView.setTextColor(Color.GRAY)
                        voteNumberTextView.setTextColor(Color.GRAY)
                        location_offline.setTextColor(Color.GRAY)
                        location_offline.text = "Offline"
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            else{
                //aqui nada ya que no deberia entrar nunca aqui
            }



        }
    }

    override fun onBindViewHolder(holder: VotingAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
    }
}