package com.example.todo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.TaskItemBinding
import com.example.todo.entities.Task

class TaskAdapter(
    var list: MutableList<Task>
):RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(var binding: TaskItemBinding):RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        TODO("Not yet implemented")
    }
}