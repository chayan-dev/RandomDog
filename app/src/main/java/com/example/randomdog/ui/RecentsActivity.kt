package com.example.randomdog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.example.randomdog.R
import com.example.randomdog.db.Dog
import com.example.randomdog.ui.resusables.ToolbarWithBackButton
import com.example.randomdog.ui.viewModels.RecentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentsActivity : ComponentActivity() {

    private lateinit var viewModel: RecentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecentViewModel::class.java]
        setContent {
            MaterialTheme {
                RecentScreen()
            }
        }
        viewModel.getAllRecentDogs()
    }

    @Composable
    fun RecentScreen() {
        Scaffold(
            topBar = {
                ToolbarWithBackButton(
                    title = stringResource(id = R.string.my_recent_generated_dogs)
                ) {
                    onBackPressedDispatcher.onBackPressed()
                }
            },
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                ) {
                    RecentListWithButton(viewModel)
                }
            }
        )
    }

    @Composable
    fun RecentListWithButton(viewModel: RecentViewModel) {
        val list = viewModel.allRecentDogs.observeAsState(emptyList())
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                RecentList(list = list.value)
                ClearButton()
            }
        }
    }

    @Composable
    fun RecentList(list: List<Dog>) {
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { dog ->
                AsyncImage(
                    model = dog.url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(300.dp)
                        .height(300.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }

    @Composable
    fun ClearButton() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp)
        )
        {
            Button(
                onClick = { viewModel.deleteAllRecentDogs() },
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
                Text(text = stringResource(id = R.string.clear_dogs))
            }
        }
    }
}