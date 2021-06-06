package com.example.project_01.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.example.project_01.MainActivity
import com.example.project_01.R
import com.example.project_01.ViewModels.UsersViewModel
import com.google.android.material.textfield.TextInputEditText

class LogInFragment : Fragment() {
    private val viewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_log_in,container, false)
        val emailInput = view.findViewById<TextInputEditText>(R.id.email_input)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.password_input)
        val logInButton = view.findViewById<Button>(R.id.log_in_button)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_buttom)


        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
            )

        viewModel.userRepository.setNavigator(activity as MainActivity)

        logInButton.setOnClickListener{
            viewModel.LogIn(emailInput.text.toString(),passwordInput.text.toString(),view,context)
            emailInput.setText("")
            passwordInput.setText("")
        }

        signUpButton.setOnClickListener{
            viewModel.userRepository.LogInFramentToSignUpFragment(view)
        }

        return view
    }






}