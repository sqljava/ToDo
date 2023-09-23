package com.example.todo.screen

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.database.AppDataBase
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.entities.Task
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding
    private var taskTitle : String = ""
    private var taskText : String = ""
    var currentFilePath: String = ""
    private lateinit var img: ImageView

    val appDataBase: AppDataBase by lazy {
        AppDataBase.getDataBsae(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        img = binding.imgAddFrag



        binding.btnSave.setOnClickListener {
            taskTitle = binding.taskInfoTitle.text.toString()
            taskText = binding.taskInfoText.text.toString()

            if (taskTitle!= "" && taskText!=""){

                appDataBase.getTaskDao().addTask(Task(
                    title = taskTitle,
                    text = taskText,
                    filePath = currentFilePath))

                parentFragmentManager.beginTransaction().
                replace(R.id.main_screen, HomeFragment()).commit()
            }else{
                Toast.makeText(requireContext(),
                "Fill fields, please", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.btnFromMemory.setOnClickListener {
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
}