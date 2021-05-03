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
        nombre=nombreInput.text.toString().trim()
        passwordInput = view.findViewById(R.id.editTextTextPassword)
        password=passwordInput.text.toString().trim()

        //communicator = activity as Communicator

        createButton.setOnClickListener {
            val result = "result of fragment"
            //communicator.passDataCom(nombre,password)


            val bundle = Bundle()
            bundle.putString("name",nombre)
            bundle.putString("password", password)
            //parentFragmentManager.setFragmentResult("key", bundle)

            //setFragmentResult("REQUEST", bundleOf("DATA" to result))
            //activity?.supportFragmentManager?.popBackStack()

            activity?.onBackPressed()



        }

        return view
    }



}