package com.example.todo.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.adapter.TaskAdapter
import com.example.todo.database.AppDataBase
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.entities.Task

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    var taskList = mutableListOf<Task>()

    val appDataBase: AppDataBase by lazy {
        AppDataBase.getDataBsae(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        taskList = appDataBase.getTaskDao().showAllTasks()

        val adapter = TaskAdapter(taskList, object :TaskAdapter.TaskInterface{
            override fun onClick(task: Task) {

                parentFragmentManager.beginTransaction().
                replace(R.id.main_screen, EditTaskFragment.newInstance(task.id)).
                addToBackStack("HomeFragment").commit()

            }

        })

        binding.homeRec.adapter = adapter

        var manager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        binding.homeRec.layoutManager = manager

        binding.btnAdd.setOnClickListener{
            parentFragmentManager.beginTransaction().
            replace(R.id.main_screen, AddTaskFragment()).addToBackStack("HomeFragment").commit()

        }
        return binding.root
    }
}