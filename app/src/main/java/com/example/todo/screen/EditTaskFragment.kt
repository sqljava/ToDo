package com.example.todo.screen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.todo.R
import com.example.todo.database.AppDataBase
import com.example.todo.databinding.FragmentEditTaskBinding
import com.example.todo.entities.Task

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


class EditTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null

    lateinit var binding:FragmentEditTaskBinding

    val appDataBase: AppDataBase by lazy {
        AppDataBase.getDataBsae(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)

        var id = param1

        var task:Task = appDataBase.getTaskDao().showTask(id)
        binding.editTaskTitle.setText(task.title)
        binding.editTaskText.setText(task.text)

        binding.btnEdit.setOnClickListener {

            var title = binding.editTaskTitle.text.toString()
            var text = binding.editTaskText.text.toString()

            task.text = text
            task.title = title

            appDataBase.getTaskDao().updateTask(task)

            Toast.makeText(requireContext(), "Task edited", Toast.LENGTH_LONG).show()

            parentFragmentManager.beginTransaction().replace(R.id.main_screen, HomeFragment()).commit()

        }

        var dialog = Dialog(requireContext())
        var dialodView = layoutInflater.inflate(R.layout.dialog, null)
        var btnYes = dialodView.findViewById<Button>(R.id.btn_yes)
        var btnNo = dialodView.findViewById<Button>(R.id.btn_no)

        binding.btnDelete.setOnClickListener {

            dialog.setContentView(dialodView)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()

            btnYes.setOnClickListener {
                appDataBase.getTaskDao().removeTask(task)

                Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_LONG).show()

                parentFragmentManager.beginTransaction().
                replace(R.id.main_screen, HomeFragment()).commit()
                dialog.hide()
            }

            btnNo.setOnClickListener {
                dialog.hide()
            }

        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}