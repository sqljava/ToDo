package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.screen.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.main_screen,HomeFragment()).commit()
    }
}