package com.example.todo.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.TaskItemBinding
import com.example.todo.entities.Task

class TaskAdapter(
    var list: MutableList<Task>,
    var taskInterface: TaskInterface
):RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(binding: TaskItemBinding):RecyclerView.ViewHolder(binding.root){
        var title = binding.taskItemTitle
        var text = binding.taskItemText
        var main = binding.taskItemMain
        var photo = binding.taskItemImg

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(TaskItemBinding.inflate(LayoutInflater.
        from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.title.text = list[position].title
        holder.text.text = list[position].text
        holder.photo.setImageURI(Uri.parse(list[position].filePath))

        holder.main.setOnClickListener {
            taskInterface.onClick(list[position])
        }
    }

    interface TaskInterface{
        fun onClick(task:Task)
    }
}