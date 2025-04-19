package com.example.tfg.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.model.user.userActivities.Activity

@Composable
fun FriendsItem(review: Activity) {
    Row(
        modifier = Modifier.Companion.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
        UserPictureWithoutCache(review.user.profilePicture, signatureKey,Modifier.size(50.dp).clip(CircleShape))

        Column {
            Row {
                Text(
                    review.user.userAlias + " ",
                    overflow = TextOverflow.Companion.Ellipsis,
                    modifier = Modifier.weight(0.60f, fill = false),
                    maxLines = 1
                )
                Text(
                    stringResource(review.infoForUI()) + " " + review.book.tittle,
                    overflow = TextOverflow.Companion.Ellipsis,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
            }
            Text(review.creationDate.toString(), color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
            if (review.extraInfo().isNotEmpty()) {
                Text(review.extraInfo(), overflow = TextOverflow.Companion.Ellipsis, maxLines = 2)
            }
            Row(modifier = Modifier.Companion.padding(top = 5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Image(
                    painterResource(R.drawable.prueba),
                    contentDescription = stringResource(id = R.string.book_image),
                    modifier = Modifier.Companion
                        .fillMaxWidth(fraction = 0.3f)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.Companion.padding(top = 5.dp)) {
                    BookTittleText(review.book.tittle)
                    BookAuthorText(review.book.author)
                    Text(review.rating.toString())
                }
            }
        }
    }
}