package com.example.project_01.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import com.example.project_01.R
import com.google.android.material.textfield.TextInputEditText

class LogInFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_log_in,container, false)
        val emailInput = view.findViewById<TextInputEditText>(R.id.email_input)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.password_input)
        val button = view.findViewById<Button>(R.id.log_in_button)
        button.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_mainFragment)
        }

        return view
    }

}