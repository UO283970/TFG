package com.example.tfg.ui.profile.components.statistics.reviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun OnlyReviews(
    returnToLastScreen: () -> Unit, viewModel: ReviewsScreenViewModel = hiltViewModel()
) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    tittle = stringResource(R.string.profile_rating_text)
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
            ) {
                HorizontalDivider()
                LazyColumn {
                    items(viewModel.profileReviewsInfo.profileReviews) {
                        ReviewItem(it)
                    }
                }
            }

        }
    }
}

@Composable
fun ReviewItem(review: Activity) {
    Row(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painterResource(review.user.profilePicture),
            contentDescription = stringResource(R.string.user_profile_image),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column {
            Row {
                Text(
                    review.user.userAlias,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
                Text(stringResource(R.string.has_reviewed) + review.book.tittle, overflow = TextOverflow.Ellipsis)
            }
            Text(review.creationDate.toString(), color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
            Text(review.extraInfo(), overflow = TextOverflow.Ellipsis, maxLines = 2)
            Row(modifier = Modifier.padding(top = 5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Image(
                    painterResource(review.book.coverImage),
                    contentDescription = stringResource(id = R.string.book_image),
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.3f)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(top = 5.dp)) {
                    Text(
                        review.book.tittle,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 24.sp,
                        maxLines = 3
                    )
                    Text(
                        review.book.author,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        maxLines = 1
                    )
                    Text(review.rating.toString())
                }
            }
        }
    }
}

