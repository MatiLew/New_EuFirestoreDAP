package com.example.eufirestoredap.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.eufirestoredap.Filosofia
import com.example.eufirestoredap.viewModels.LoginViewModel
import com.example.eufirestoredap.R
import com.example.eufirestoredap.Users
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    lateinit var v: View
    lateinit var logInButton: Button
    lateinit var registerText: TextView
    lateinit var userName: EditText
    lateinit var userPassword: EditText
    lateinit var progressBar: ProgressBar

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private val db = Firebase.firestore
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_login, container, false)

        logInButton = v.findViewById(R.id.loginButton)
        registerText = v.findViewById(R.id.registerText)
        userName = v.findViewById(R.id.editTextName)
        userPassword = v.findViewById(R.id.editTextPassword)
        progressBar = v.findViewById(R.id.progressBar)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        progressBar.visibility = View.INVISIBLE
        logInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            logInClick(userName.text.toString(), userPassword.text.toString())
        }


        registerText.setOnClickListener {
            onRegisterClick()
        }

    }

    fun logInClick(userName: String, userPassword: String) {
        if(!userName.isEmpty() && !userPassword.isEmpty()) {
            mAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener {
                if(it.isSuccessful) {
                    progressBar.visibility = View.INVISIBLE
                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    v.findNavController().navigate(action)
                }
                else {
                    progressBar.visibility = View.INVISIBLE
                    Snackbar.make(v, "Datos incorrectos", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        else {
            Snackbar.make(v, "Por favor, llene los espacios.", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun onRegisterClick() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        v.findNavController().navigate(action)
    }

}