package com.example.tfg.ui.bookDetails.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.ui.bookDetails.DescriptionText
import com.example.tfg.ui.common.UserPictureWithoutCache
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReviewsScreen(returnToLastScreen: () -> Unit, reviewsScreenViewModel: ReviewsScreenViewModel = hiltViewModel()) {
    TFGTheme(dynamicColor = false){
        Scaffold(topBar = {
            TopDetailsListBar(returnToLastScreen, stringResource(R.string.book_details_review_screen))
        }) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .then(Modifier.padding(start = 10.dp, end = 10.dp))
            ) {
                items(reviewsScreenViewModel.bookReviewState.listOfReviews) {
                    Column(verticalArrangement = Arrangement.spacedBy(15.dp), modifier = Modifier.padding(bottom = 15.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
                            UserPictureWithoutCache(
                                it.user.profilePicture, signatureKey, Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                            Column {
                                Text(it.user.userAlias, overflow = TextOverflow.Ellipsis)
                                Text(it.creationDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())))
                            }
                            if(reviewsScreenViewModel.mainUserState.getMainUser()?.userId == it.user.userId){
                                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                                    IconButton({}) {
                                        Icon(Icons.Default.MoreVert, null)
                                    }
                                }
                            }
                        }
                        ReviewText(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewText(activity: ReviewActivity) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        Column (Modifier
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)){
            Text(stringResource(R.string.book_details_review_screen_score, if (activity.rating == -1) "-" else activity.rating.toString()))
            DescriptionText(activity.reviewText, 4)
        }
    }
}