package com.example.randomdog

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomdog.ui.GenerateActivity
import com.example.randomdog.ui.RecentsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainScreen(
                        onGenerateClick = {
                            startActivity(Intent(this, GenerateActivity::class.java))
                        },
                        onRecentClick = {
                            startActivity(Intent(this, RecentsActivity::class.java))
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        onGenerateClick: () -> Unit,
        onRecentClick: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val spaceBetweenItems = 24.dp

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.random_dog_generator),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.black)
                )

                Text(
                    text = stringResource(id = R.string.generate_dogs),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .padding(top = 200.dp)
                        .background(
                            color = colorResource(id = R.color.blue),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onGenerateClick() }
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                )

                Text(
                    text = stringResource(id = R.string.my_recent_generated_dogs),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(
                            color = colorResource(id = R.color.blue),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onRecentClick() }
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                )
            }
        }
    }

}