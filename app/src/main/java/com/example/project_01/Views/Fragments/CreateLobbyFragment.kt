package com.example.project_01.Views.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.project_01.Models.Lobby
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel

class CreateLobbyFragment : Fragment() {

    lateinit var nombreInput: EditText
    lateinit var passwordInput: EditText
    lateinit var deckInput : Spinner
    private val viewModel: CardsViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_room, container, false)
        val createButton = view.findViewById<Button>(R.id.button_create_room)
        nombreInput = view.findViewById(R.id.edit_name)
        passwordInput = view.findViewById(R.id.edit_password)
        deckInput = view.findViewById(R.id.spinner_create)

        viewModel.deckSelector = deckInput
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, viewModel.Decks)
        deckInput.adapter = adapter
        val pos = viewModel.Decks.indexOf(viewModel.currentDeck)
        deckInput.setSelection(pos)
        var deck = viewModel.Decks[0]
        deckInput.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                deck = viewModel.Decks[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        createButton.setOnClickListener {
            val bundle = bundleOf(
                "name" to nombreInput.text.toString(),
                "pass" to passwordInput.text.toString(),
                "deck" to deck
            )
            setFragmentResult("REQUEST_ROOM", bundle)
            activity?.onBackPressed()



        }
        Toast.makeText(context,"To delete swipe right", Toast.LENGTH_SHORT).show()

        return view
    }



}