package com.example.todo.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            var task1 = Task(title = title,
                        text = text)

            appDataBase.getTaskDao().updateTask(task)


            parentFragmentManager.beginTransaction().replace(R.id.main_screen, HomeFragment()).commit()

        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}