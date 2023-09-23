package com.example.todo.screen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.todo.R
import com.example.todo.database.AppDataBase
import com.example.todo.databinding.FragmentEditTaskBinding
import com.example.todo.entities.Task
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


class EditTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null

    lateinit var binding:FragmentEditTaskBinding
    var currentFilePath: String = ""
    private lateinit var img: ImageView

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
        img = binding.imgEditFrag

        var id = param1

        var task:Task = appDataBase.getTaskDao().showTask(id)
        binding.editTaskTitle.setText(task.title)
        binding.editTaskText.setText(task.text)

        binding.btnEdit.setOnClickListener {

            var title = binding.editTaskTitle.text.toString()
            var text = binding.editTaskText.text.toString()

            if (task.title==title && task.text==text){
                Toast.makeText(requireContext(), "Task not edited", Toast.LENGTH_LONG).show()
            }else{
                task.text = text
                task.title = title
                if(currentFilePath != ""){
                    task.filePath = currentFilePath
                }

                appDataBase.getTaskDao().updateTask(task)

                Toast.makeText(requireContext(), "Task edited", Toast.LENGTH_LONG).show()

                parentFragmentManager.beginTransaction().replace(R.id.main_screen, HomeFragment()).commit()
            }
        }

        var dialog = Dialog(requireContext())
        var dialodView = layoutInflater.inflate(R.layout.dialog, null)
        var btnYes = dialodView.findViewById<Button>(R.id.btn_yes)
        var btnNo = dialodView.findViewById<Button>(R.id.btn_no)

        binding.btnDelete.setOnClickListener {

            dialog.setContentView(dialodView)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.linearParentDialog.setBackgroundColor(Color.GRAY)

            dialog.show()

            btnYes.setOnClickListener {
                appDataBase.getTaskDao().removeTask(task)

                Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_LONG).show()

                parentFragmentManager.beginTransaction().
                replace(R.id.main_screen, HomeFragment()).commit()
                dialog.hide()
                binding.linearParentDialog.setBackgroundColor(Color.TRANSPARENT)
            }

            btnNo.setOnClickListener {
                dialog.hide()
                binding.linearParentDialog.setBackgroundColor(Color.TRANSPARENT)
            }

        }

        if (task.filePath != ""){
            binding.imgEditFrag.setImageURI(Uri.parse(task.filePath))
        }


        binding.btnCameraEdit.setOnClickListener {
            dispatchTakePictureIntent()

        }


        binding.btnFromMemoryEdit.setOnClickListener {
            takePhotoResult.launch("image/*")

        }



        return binding.root
    }

    val takePhotoResult =registerForActivityResult(ActivityResultContracts.
    GetContent()){uri->
        if (uri == null) return@registerForActivityResult

        img.setImageURI(uri)

        val openInputStream = requireActivity().contentResolver?.openInputStream(uri)
        val file = File(requireActivity().filesDir, "${System.currentTimeMillis()}.jpg")
        val fileOutputStream = FileOutputStream(file)
        openInputStream?.copyTo(fileOutputStream)
        currentFilePath = file.absolutePath
        openInputStream?.close()

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentFilePath = absolutePath
        }
    }
    private fun dispatchTakePictureIntent() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }

        photoFile?.let {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "package com.example.todo",
                it
            )
            takePhotoResultCamera.launch(photoURI)
        }
    }
    val takePhotoResultCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            img.setImageURI(Uri.fromFile(File(currentFilePath)))
        }
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