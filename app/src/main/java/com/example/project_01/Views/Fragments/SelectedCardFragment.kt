package com.example.project_01.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.project_01.R

class SelectedCardFragment : Fragment() {

    val args : SelectedCardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selected_card, container, false)
        val text = view.findViewById<TextView>(R.id.show_text)
        text.text=args.number
        text.setOnClickListener{
            activity?.onBackPressed()
        }
        return view
    }

}