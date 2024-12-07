package com.example.randomdog.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.bumptech.glide.Glide
import com.example.randomdog.R
import com.example.randomdog.databinding.ActivityGenerateBinding
import com.example.randomdog.ui.viewModels.DogsViewModel
import com.example.randomdog.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGenerateBinding
    private lateinit var viewModel: DogsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DogsViewModel::class.java]
        setToolbar()
        binding.composeView.setContent {
            MaterialTheme{
                GenerateDog(viewModel)
            }
        }
        viewModel.getRandomDog()
    }

    @Composable
    fun GenerateDog(viewModel: DogsViewModel){
        val response = viewModel.randomDog.observeAsState(initial = Resource.Loading())
        when(response.value){
            is Resource.Success -> {
                ProgressBar(isVisible = false)
                response.value.data?.let {
                    GenerateDogUI(url = it.message)
                }
            }
            is Resource.Error -> {
                ProgressBar(isVisible = false)
                Toast.makeText(this,"Some error has occurred", Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> {
                ProgressBar(isVisible = true)
            }
        }
    }

    @Composable
    fun GenerateDogUI(url: String){
        Surface {
            Column (
                Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(32.dp) ,
                    contentScale = ContentScale.Fit,
                    )
                Button(
                    onClick = { viewModel.getRandomDog()},
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(ContextCompat.getColor(this@GenerateActivity, R.color.blue)),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .height(32.dp)
                ) {
                    Text(text = "Generate!")

                }
            }

        }
    }

    @Composable
    fun ProgressBar(
        isVisible: Boolean,
        modifier: Modifier = Modifier
    ) {
        Log.d("progress_is", isVisible.toString())
        if (isVisible) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Color(ContextCompat.getColor(this@GenerateActivity, R.color.blue))
                )
            }
        }
    }

    private fun setToolbar(){
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
}