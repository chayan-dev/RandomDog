package com.example.randomdog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomdog.databinding.ActivityMainBinding
import com.example.randomdog.ui.GenerateActivity
import com.example.randomdog.ui.RecentsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickedListener()
    }

    private fun setOnClickedListener(){
        binding.btnGenerate.setOnClickListener {
            startActivity(Intent(this, GenerateActivity::class.java))
        }

        binding.btnRecent.setOnClickListener {
            startActivity(Intent(this, RecentsActivity::class.java))
        }
    }
}