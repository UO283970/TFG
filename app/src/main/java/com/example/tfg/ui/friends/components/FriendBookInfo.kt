package com.example.tfg.ui.friends.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.model.user.userActivities.Activity

@Composable
fun activityAndBookInfo(activity: Activity) {
    Column {
        Text(
            stringResource(activity.infoForUI()),
            maxLines = 2,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            text = activity.book.tittle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun bookImageBig(coverImage: Int, weight: Modifier) {
    Image(
        painterResource(coverImage),
        contentDescription = stringResource(id = R.string.book_image),
        modifier = Modifier.height(150.dp)
            .clip(RoundedCornerShape(10.dp))

    )
}