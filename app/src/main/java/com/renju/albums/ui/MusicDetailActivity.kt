package com.renju.albums.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.loyverse.ui.theme.DarkBlue
import com.example.loyverse.ui.theme.LoyverseMusicAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicDetailActivity : ComponentActivity() {
    private val viewModel: MusicListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            MusicDetailCard(viewModel = viewModel)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}