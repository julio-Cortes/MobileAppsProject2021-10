package com.example.project_01.Views

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import java.io.File
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.project_01.Models.Room
import com.example.project_01.R
import java.io.BufferedWriter
import java.io.FileWriter

class CreateRoomFragment : Fragment() {

    lateinit var nombre: String
    lateinit var nombreInput: EditText
    lateinit var password: String
    lateinit var passwordInput: EditText
    //lateinit var communicator: Communicator

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_room, container, false)
        val createButton = view.findViewById<Button>(R.id.buttonCreateRoom)
        nombreInput = view.findViewById(R.id.editTextTextPersonName)
        passwordInput = view.findViewById(R.id.editTextTextPassword)


        createButton.setOnClickListener {
            setFragmentResult("REQUEST_ROOM", bundleOf("ROOM" to Room(nombreInput.text.toString(),passwordInput.text.toString())))
            activity?.onBackPressed()



        }

        return view
    }



}