package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp.databinding.HomeFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbreference: DatabaseReference
    private lateinit var navController: NavController
    private lateinit var binding: HomeFragmentBinding
    private lateinit var popupFragment: AddTodoPopupFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        onAddEvents()

    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        dbreference = FirebaseDatabase.getInstance().reference
    }

    private fun onAddEvents() {
        binding.addHomeFAB.setOnClickListener {
            popupFragment = AddTodoPopupFragment()
            popupFragment.setListener(this)
            popupFragment.show(
                childFragmentManager, "AddTodoPopupFragment"
            )
        }
    }

    override fun onSaveTask(todo: String, todoET: TextInputEditText) {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            dbreference.child("users").child(userId).child("tasks").push()
                .setValue(todo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Todo Task is saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        todoET.text = null
                    } else {
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                    popupFragment.dismiss()
                }
        } else {
            Toast.makeText(
                context,
                "User not authenticated. Please log in again.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
