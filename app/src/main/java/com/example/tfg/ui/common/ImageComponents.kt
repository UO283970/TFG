package com.example.tfg.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.signature.ObjectKey
import com.example.tfg.R

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun UserPictureWithoutCache(profilePicture: String, signatureKey: MutableState<String>, modifier: Modifier) {
    GlideImage(
        model = profilePicture,
        contentDescription = stringResource(R.string.user_profile_image),
        loading = placeholder(R.drawable.default_user_image),
        failure = placeholder(R.drawable.default_user_image),
        transition = CrossFade.Companion,
        modifier = modifier,
        contentScale = ContentScale.Companion.FillBounds
    ) {
        it.signature(ObjectKey(signatureKey.value))
    }
}