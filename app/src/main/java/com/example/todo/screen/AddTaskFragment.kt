package com.example.todo.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.database.AppDataBase
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.entities.Task

class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding
    private var taskTitle : String = ""
    private var taskText : String = ""

    val appDataBase: AppDataBase by lazy {
        AppDataBase.getDataBsae(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)



        binding.btnSave.setOnClickListener {
            taskTitle = binding.taskInfoTitle.text.toString()
            taskText = binding.taskInfoText.text.toString()

            if (taskTitle!= "" && taskText!=""){

                appDataBase.getTaskDao().addTask(Task(
                    title = taskTitle,
                    text = taskText))

                parentFragmentManager.beginTransaction().
                replace(R.id.main_screen, HomeFragment()).commit()
            }
        }
        return binding.root
    }
}