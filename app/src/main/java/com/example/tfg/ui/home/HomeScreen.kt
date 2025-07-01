package com.example.tfg.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tfg.R
import com.example.tfg.model.book.Book
import com.example.tfg.ui.common.LoadingProgress
import com.example.tfg.ui.common.TittleBigText
import com.example.tfg.ui.common.navHost.ListNavigationItems
import com.example.tfg.ui.home.components.NoBooksMainScreen
import com.example.tfg.ui.home.components.TopNotifications
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun HomeScreen(navigateTo: (route: String) -> Unit, bottomBarState: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.homeState.collectAsState()

    if (state.loadingInfo) {
        bottomBarState()
        TFGTheme(dynamicColor = false) {
            Scaffold(
                contentWindowInsets = WindowInsets(0.dp),
                topBar = { TopNotifications(navigateTo, state.hasNotifications) }) { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    HorizontalDivider(thickness = 1.dp)
                    Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                        if (viewModel.listsState.getDefaultLists()[0].books.isEmpty()) {
                            NoBooksMainScreen(
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(), navigateTo
                            )
                        } else {
                            Column(Modifier.padding(20.dp)) {
                                TittleBigText(stringResource(id = R.string.home_reading_books))
                                FanAnimation(
                                    viewModel.listsState.getDefaultLists()[0].books,
                                    Modifier.fillMaxSize(0.8f),
                                    state.animate,
                                    { viewModel.goToReading() },
                                    { navigateTo(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        LoadingProgress()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FanAnimation(images: ArrayList<Book>, modifier: Modifier, animate: Boolean, goToReading: () -> Unit, navigateTo: (String) -> Unit) {
    val totalAngle = 20f
    val imageCount = images.size
    val angleStep = if (imageCount > 1) totalAngle / (imageCount - 1) else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                goToReading()
                navigateTo(ListNavigationItems.ListDetails.route)
            },
        contentAlignment = Alignment.Center
    ) {
        images.forEachIndexed { index, imageRes ->
            val angle = -totalAngle / 2 + angleStep * index
            val rotation by animateFloatAsState(
                targetValue = if (animate) angle else 0f,
                animationSpec = tween(600)
            )


            GlideImage(
                model = imageRes.coverImage,
                contentDescription = stringResource(R.string.book_cover),
                failure = placeholder(R.drawable.no_cover_image_book),
                transition = CrossFade,
                modifier = Modifier
                    .graphicsLayer(
                        rotationZ = rotation,
                        transformOrigin = TransformOrigin(0f, 1f)
                    )
                    .padding(top = 5.dp)
                    .then(modifier)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            ) {
                it.skipMemoryCache(true)
            }
        }
    }
}


