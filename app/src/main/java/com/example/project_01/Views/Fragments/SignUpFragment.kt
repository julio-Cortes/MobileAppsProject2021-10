package com.example.project_01.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel
import com.example.project_01.ViewModels.UsersViewModel
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : Fragment() {

    private val viewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val emailInput = view.findViewById<TextInputEditText>(R.id.email_input)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.password_input)
        val nameInput = view.findViewById<TextInputEditText>(R.id.name_input)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_buttom)
        signUpButton.setOnClickListener{
            viewModel.SignUp(nameInput.text.toString(),emailInput.text.toString(),passwordInput.text.toString(),view,context)
        }
        return view
    }

}