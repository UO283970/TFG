package com.example.tfg.ui.bookDetails.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.bookDetails.BookDetailsViewModel
import com.skydoves.cloudy.cloudy


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MainBookInfoImage(viewModel: BookDetailsViewModel) {
    val constraints = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier.height(constraints * 0.4f)
    ) {
        Image(
            painterResource(viewModel.bookInfo.book.coverImage),
            stringResource(R.string.book_image),
            Modifier
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                .cloudy(15)
                .fillMaxHeight(fraction = 0.9f),
            contentScale = ContentScale.Companion.Crop
        )
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 5.dp)
        ) {
            Image(
                painterResource(viewModel.bookInfo.book.coverImage),
                stringResource(R.string.book_image),
                Modifier.Companion
                    .fillMaxHeight(fraction = 0.9f)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            )
        }
    }
}