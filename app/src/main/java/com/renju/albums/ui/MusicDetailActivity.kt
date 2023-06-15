package com.renju.albums.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.loyverse.ui.theme.DarkBlue
import com.example.loyverse.ui.theme.LoyverseMusicAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicDetailActivity : ComponentActivity() {
    private val viewModel: MusicListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMusicList()
        setContent {
            LoyverseMusicAppTheme {
                Scaffold(
                    topBar = { SearchAppBar(viewModel) },
                ) { padding ->

                    Box(
                        modifier = Modifier.padding(padding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DarkBlue)
                        ) {
                            MusicDetailCard(state = viewModel.state)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        if (viewModel.state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Center)
                            )
                        }
                        viewModel.state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Center)
                            )
                        }
                    }
                }
            }
        }
    }
}