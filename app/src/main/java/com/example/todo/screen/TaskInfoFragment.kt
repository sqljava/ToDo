package com.example.todo.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.database.AppDataBase
import com.example.todo.databinding.FragmentTaskInfoBinding
import com.example.todo.entities.Task

class TaskInfoFragment : Fragment() {
    lateinit var binding: FragmentTaskInfoBinding
    private var taskTitle : String = ""
    private var taskText : String = ""

    val appDataBase: AppDataBase by lazy {
        AppDataBase.getDataBsae(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskInfoBinding.inflate(inflater, container, false)

        var args = this.arguments
        var input = args?.getInt("task_id")



        //binding.taskInfoTitle.text = input?.toString()





        binding.btnSave.setOnClickListener {
            taskTitle = binding.taskInfoTitle.text.toString()
            taskText = binding.taskInfoText.text.toString()

            appDataBase.getTaskDao().addTask(Task(
                title = taskTitle,
                text = taskText))

            findNavController().navigate(R.id.action_taskInfoFragment_to_homeFragment)
        }
        return binding.root
    }
}