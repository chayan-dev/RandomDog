package com.example.randomdog.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.randomdog.R
import com.example.randomdog.databinding.ActivityGenerateBinding
import com.example.randomdog.ui.viewModels.DogsViewModel
import com.example.randomdog.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerateBinding
    private lateinit var viewModel: DogsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        viewModel = ViewModelProvider(this)[DogsViewModel::class.java]
        setObservers()
//        viewModel.getRandomDog()
        setOnClickListener()
    }

    private fun setObservers() {
        viewModel.randomDog.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideLoader()
                    response.data?.let {
                        Glide.with(this).load(it.message).into(binding.ivDog)
                    }
                }

                is Resource.Error -> {
                    hideLoader()
                    Toast.makeText(this, "Some error has occurred", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    showLoader()
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnGenerate.setOnClickListener {
            viewModel.getRandomDog()
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(
            AppCompatResources.getDrawable(this, R.drawable.ic_back)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.loader.visibility = View.GONE
    }
}