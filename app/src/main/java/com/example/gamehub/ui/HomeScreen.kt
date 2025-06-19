package com.example.gamehub.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gamehub.domain.model.Game
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    /* ---------- state ---------- */

    val context            = LocalContext.current
    val snackbarHostState  = remember { SnackbarHostState() }
    val scope              = rememberCoroutineScope()

    // предполагаем, что во ViewModel есть flow-пара uiState { isRefreshing, isOffline }
    val uiState by viewModel.isOffline.collectAsState()
    val newReleases = viewModel.newReleases.collectAsLazyPagingItems()
    val topRated = viewModel.topRated.collectAsLazyPagingItems()

    // если нужна кастомизация жёстче дефолтной — храните state отдельно
    val pullState = rememberPullToRefreshState()

    /* ---------- side-effects ---------- */

    LaunchedEffect(uiState) {
        if (uiState)  snackbarHostState.showSnackbar("Offline mode")
        else                    snackbarHostState.currentSnackbarData?.dismiss()
    }

    /* ---------- UI ---------- */

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddings ->

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
            state = pullState,
            isRefreshing = false,
            onRefresh = {
                if (uiState) {
                    Toast.makeText(context,
                        "Невозможно обновить без сети",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // LazyPagingItems will handle refresh
                    newReleases.refresh()
                    topRated.refresh()
                }
            }
        ) {
            Content(
                newReleases = newReleases,
                topRated = topRated
            )
        }
    }
}

@Composable
private fun Content(
    newReleases: LazyPagingItems<Game>,
    topRated: LazyPagingItems<Game>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            TopAppBar(
                title = { Text("GameHub") }
            )
        }
        item { BannerCarousel(topRated) }
        item { CategorySection("Новые релизы", newReleases) }
        item { CategorySection("Популярные", topRated) }
        item { CategorySection("Рекомендации для вас", topRated) }
    }
}

@Composable
private fun BannerCarousel(items: LazyPagingItems<Game>) {
    val count = minOf(items.itemCount, 5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(count) { index ->
            val game = items[index]
            if (game != null) {
                AsyncImage(
                    model = game.backgroundImage,
                    contentDescription = game.name,
                    modifier = Modifier
                        .height(180.dp)
                        .fillParentMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CategorySection(title: String, items: LazyPagingItems<Game>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { /*TODO*/ }) { Text("Показать все") }
        }
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(items.itemCount) { index ->
                val game = items[index]
                if (game != null) {
                    GameCard(game)
                }
            }
        }
    }
}

@Composable
private fun GameCard(game: Game) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(end = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = game.backgroundImage,
            contentDescription = game.name,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = game.name, style = MaterialTheme.typography.bodyMedium)
            game.rating?.let {
                Text(text = "★${String.format("%.1f", it)}")
            }
        }
    }
}
