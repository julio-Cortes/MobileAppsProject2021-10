package com.example.project_01.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel


class JoinLobbyFragment : Fragment() {

    lateinit var nombreInput: EditText
    lateinit var passwordInput: EditText
    private val viewModel: CardsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_join_lobby, container, false)
        val joinButton = view.findViewById<Button>(R.id.button_join_room)
        nombreInput = view.findViewById(R.id.edit_name_join)
        passwordInput = view.findViewById(R.id.edit_password_join)


        joinButton.setOnClickListener {
            val bundle = bundleOf(
                "name" to nombreInput.text.toString(),
                "pass" to passwordInput.text.toString()
            )
            setFragmentResult("REQUEST_ROOM_JOIN", bundle)
            activity?.onBackPressed()
        }


        return view
    }


}