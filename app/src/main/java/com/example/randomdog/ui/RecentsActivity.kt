package com.example.randomdog.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.example.randomdog.R
import com.example.randomdog.databinding.ActivityRecentsBinding
import com.example.randomdog.ui.viewModels.RecentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRecentsBinding
    private lateinit var viewModel: RecentViewModel
    lateinit var adapter: DogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        viewModel = ViewModelProvider(this)[RecentViewModel::class.java]
        initAdapter()
        setObservers()
        viewModel.getAllRecentDogs()
        setOnClickListener()

    }

    private fun setObservers(){
        viewModel.allRecentDogs.observe(this){ list ->
            adapter.setDogs(list)
        }
    }

    private fun setOnClickListener(){
        binding.btnClear.setOnClickListener {
            viewModel.deleteAllRecentDogs()
        }

    }

    private fun initAdapter(){
        adapter = DogAdapter()
        binding.rcyRecent.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(
            AppCompatResources.getDrawable(this, R.drawable.ic_back)
        )
    }
}