package com.example.project_01.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.project_01.MainActivity
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel
import org.koin.android.ext.android.inject
import java.lang.Exception


class SettingsFragment : Fragment() {
    private val viewModel: CardsViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val deckSelector = view.findViewById<Spinner>(R.id.spinner)


        viewModel.deckSelector = deckSelector
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, viewModel.Decks)
        deckSelector.adapter = adapter

        try {
            val pos = viewModel.Decks.indexOf(viewModel.currentDeck)
            deckSelector.setSelection(pos)
        }
        catch (exception:Exception){

        }





        deckSelector.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                viewModel.changeDeck(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.changeDeck(0)
            }

        }

        return view

    }



}