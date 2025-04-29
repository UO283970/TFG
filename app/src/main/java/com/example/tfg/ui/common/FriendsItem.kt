package com.example.tfg.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tfg.R
import com.example.tfg.model.book.Book
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.ui.common.navHost.BookNavigationItems

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FriendsItem(
    review: Activity,
    navigateToProfile: (User) -> Unit,
    setDetailsBook: (book: Book) -> Unit,
    navigateTo: (route: String) -> Unit,
    navigateToSearch: (author: String, searchFor: String) -> Unit
) {
    Row(
        modifier = Modifier.Companion.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
        UserPictureWithoutCache(review.user.profilePicture, signatureKey, Modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable { navigateToProfile(review.user) })

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    review.user.userAlias + " ",
                    overflow = TextOverflow.Companion.Ellipsis,
                    modifier = Modifier.widthIn(max = (LocalWindowInfo.current.containerSize.width.dp * 0.1f)),
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
            Row(modifier = Modifier.Companion
                .padding(top = 5.dp)
                .clickable {
                    setDetailsBook(review.book)
                    navigateTo(BookNavigationItems.BookScreen.route)
                }, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlideImage(
                    model = review.book.coverImage,
                    contentDescription = stringResource(R.string.user_profile_image),
                    failure = placeholder(R.drawable.no_cover_image_book),
                    transition = CrossFade.Companion,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.3f)
                        .fillMaxHeight(fraction = 0.5f)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Companion.FillBounds
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.Companion.padding(top = 5.dp)) {
                    BookTittleText(review.book.tittle)
                    BookAuthorText(review.book.author,navigateToSearch)
                    Text(review.rating.toString())
                }
            }
        }
    }
}