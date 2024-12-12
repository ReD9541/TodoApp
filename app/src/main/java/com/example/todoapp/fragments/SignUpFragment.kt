package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.example.todoapp.R
import com.example.todoapp.databinding.SignupFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: SignupFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View?  {

        binding = SignupFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun init(view:View){
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun registerEvents() {
        binding.AuthTextView.setOnClickListener{
            navController.navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.NextBtn.setOnClickListener{
            val email = binding.EmailET.text.toString().trim()
            val pass = binding.PasswordET.text.toString().trim()
            val rePass = binding.ConfirmPasswordET.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty() && rePass.isNotEmpty()){
                if (pass == rePass){
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(context ,"Registration Successful!! You may now Login..", Toast.LENGTH_SHORT).show()
                                navController.navigate(R.id.action_signUpFragment_to_loginFragment)
                            }else{
                                Toast.makeText(context , it.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }

        }
    }
}
