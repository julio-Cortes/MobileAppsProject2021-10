package com.example.project_01.Views.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.project_01.Models.Lobby
import com.example.project_01.R

class CreateLobbyFragment : Fragment() {

    lateinit var nombreInput: EditText
    lateinit var passwordInput: EditText

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_room, container, false)
        val createButton = view.findViewById<Button>(R.id.button_create_room)
        nombreInput = view.findViewById(R.id.edit_name)
        passwordInput = view.findViewById(R.id.edit_password)


        createButton.setOnClickListener {
            val bundle = bundleOf(
                "name" to nombreInput.text.toString(),
                "pass" to passwordInput.text.toString()
            )
            setFragmentResult("REQUEST_ROOM", bundle)
            activity?.onBackPressed()



        }
        Toast.makeText(context,"Para eliminar swipe right", Toast.LENGTH_SHORT).show()

        return view
    }



}