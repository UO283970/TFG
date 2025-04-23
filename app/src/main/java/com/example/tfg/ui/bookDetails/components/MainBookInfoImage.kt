package com.example.tfg.ui.bookDetails.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tfg.R
import com.skydoves.cloudy.cloudy


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MainBookInfoImage(coverImage: String, color: Color, textColor: Color, returnToLastScreen: () -> Unit) {
    val constraints = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier.height(constraints * 0.4f)
    ) {
        GlideImage(
            model = coverImage,
            contentDescription = stringResource(R.string.book_cover),
            failure = placeholder(R.drawable.no_cover_image_book),
            transition = CrossFade,
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                .cloudy(15)
                .fillMaxWidth()
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
            GlideImage(
                model = coverImage,
                contentDescription = stringResource(R.string.book_cover),
                failure = placeholder(R.drawable.no_cover_image_book),
                transition = CrossFade,
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.9f)
                    .fillMaxWidth(fraction = 0.40f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Companion.FillBounds
            )
        }
        Box(modifier = Modifier.padding(top = 10.dp, start = 10.dp).clip(CircleShape).background(color).zIndex(2f)){
            IconButton({returnToLastScreen()}) {
                Icon(Icons.AutoMirrored.Default.ArrowBack,null, tint = textColor)
            }
        }
    }
}