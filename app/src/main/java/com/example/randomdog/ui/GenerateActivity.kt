package com.example.randomdog.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.example.randomdog.R
import com.example.randomdog.api.RandomDogResponse
import com.example.randomdog.ui.resusables.ToolbarWithBackButton
import com.example.randomdog.ui.viewModels.DogsViewModel
import com.example.randomdog.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateActivity : ComponentActivity() {

    private lateinit var viewModel: DogsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DogsViewModel::class.java]
        setContent {
            MaterialTheme {
                GenerateScreen()
            }
        }
    }

    @Composable
    fun GenerateScreen() {
        Scaffold(
            topBar = {
                ToolbarWithBackButton(title = stringResource(id = R.string.generate_dogs)) {
                    onBackPressedDispatcher.onBackPressed()
                }
            },
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                ) {
                    GenerateDog(viewModel)
                    GenerateButton()
                }
            }
        )
    }

    @Composable
    fun GenerateDog(viewModel: DogsViewModel) {
        val response = viewModel.randomDog.observeAsState(
            initial = Resource.Success(
                RandomDogResponse(
                    "",
                    ""
                )
            )
        )
        when (response.value) {
            is Resource.Success -> {
                ProgressBar(isVisible = false)
                response.value.data?.let {
                    GenerateDogUI(url = it.message)
                }
            }

            is Resource.Error -> {
                ProgressBar(isVisible = false)
                Toast.makeText(this, stringResource(id = R.string.error), Toast.LENGTH_SHORT).show()
            }

            is Resource.Loading -> {
                ProgressBar(isVisible = true)
            }
        }
    }

    @Composable
    fun GenerateDogUI(url: String) {
        Column(
            Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(32.dp),
                contentScale = ContentScale.Fit,
            )

        }

    }

    @Composable
    fun GenerateButton() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp)
        ) {
            Button(
                onClick = { viewModel.getRandomDog() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.blue),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(bottom = 150.dp)
                    .height(32.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(id = R.string.generate))
            }
        }
    }

    @Composable
    fun ProgressBar(
        isVisible: Boolean,
        modifier: Modifier = Modifier
    ) {
        if (isVisible) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.blue)
                )
            }
        }
    }
}