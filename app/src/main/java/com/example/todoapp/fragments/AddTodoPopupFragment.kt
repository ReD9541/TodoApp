package com.example.todoapp.fragments

import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.compose.LifecycleStopOrDisposeEffectResult
import com.example.todoapp.R
import com.example.todoapp.databinding.AddTodoPopupFragmentBinding
import com.example.todoapp.databinding.HomeFragmentBinding
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding: AddTodoPopupFragmentBinding
    private lateinit var listener: DialogNextBtnClickListener

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = AddTodoPopupFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTodoEvent()
    }

    private fun addTodoEvent() {
        binding.TodoNextbtn.setOnClickListener {
            val todoTask = binding.TodotaskET.text.toString()
            if (todoTask.isNotEmpty()) {
                Toast.makeText(context, "Saving your task", Toast.LENGTH_SHORT).show()
                listener.onSaveTask(todoTask, binding.TodotaskET)
            } else {
                Toast.makeText(context, "Please type your task first", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoET: TextInputEditText)

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

}