package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.LoginFragmentBinding
import com.example.todoapp.databinding.SignupFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        loginEvents()
    }

    private fun init(view:View){
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun loginEvents() {
        binding.AuthTextView.setOnClickListener{
            navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.NextBtn.setOnClickListener{
            val email = binding.EmailET.text.toString().trim()
            val pass = binding.PasswordET.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(context ,"Login Successful", Toast.LENGTH_SHORT).show()
                                navController.navigate(R.id.action_loginFragment_to_homeFragment)
                            }else{
                                Toast.makeText(context , it.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
            }

        }
    }
}