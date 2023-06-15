package com.renju.albums.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.renju.albums.R
import com.renju.albums.domain.model.MusicDetails

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicDetailCard(
    viewModel: MusicListViewModel,
) {
    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.refreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) {

        viewModel.state.musicDetailsList?.let { data ->
            if (data.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    items(items = data, itemContent = {
                        MusicListItem(musicDetails = it)
                    })
                }
            }
        }

        viewModel.state.error?.let { error ->
            Box(modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
                .clickable { viewModel.refreshData() }) {
                Text(
                    text = error + stringResource(id = R.string.click_here),
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        }
        if (viewModel.state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        PullRefreshIndicator(
            viewModel.refreshing,
            refreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun MusicListItem(
    musicDetails: MusicDetails
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row {
            MusicImage(musicDetails.musicImageUrl)
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .clickable {
                    musicDetails.musicLink?.let { launchWebBrowser(launcher, it) }
                }
            ) {
                Text(text = musicDetails.musicTitle, style = MaterialTheme.typography.h6)
                Text(
                    text = stringResource(R.string.view_detail),
                    style = MaterialTheme.typography.caption
                )

            }

        }
    }
}

@Composable
private fun MusicImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

private fun launchWebBrowser(
    launcher: ActivityResultLauncher<Intent>, url: String
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    launcher.launch(intent)
}